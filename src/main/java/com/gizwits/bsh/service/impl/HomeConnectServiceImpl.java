package com.gizwits.bsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gizwits.bsh.bean.*;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.bean.resvo.DeviceResVO;
import com.gizwits.bsh.bean.resvo.DeviceStatusResVO;
import com.gizwits.bsh.mapper.BSHTokenMapper;
import com.gizwits.bsh.mapper.DeviceMapper;
import com.gizwits.bsh.model.entity.BSHToken;
import com.gizwits.bsh.model.entity.Device;
import com.gizwits.bsh.service.HomeConnectService;
import com.gizwits.bsh.util.GeneratorKit;
import com.gizwits.bsh.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.cheaa.interconnection.spi.IUDeviceIdConvert;
import org.springframework.scheduling.annotation.Async;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 博西 Home Connect API
 */
@Service
public class HomeConnectServiceImpl implements HomeConnectService {

    private static Logger logger = LoggerFactory.getLogger(HomeConnectServiceImpl.class);

    @Autowired
    IUDeviceIdConvert udeviceIdConvert;
    
    @Autowired
    BSHTokenMapper bshTokenMapper;

    @Autowired
    DeviceMapper deviceMapper;

    private static final String host = "https://api.home-connect.cn";
    private static Date unblockTime = null;
    private static final Boolean debug = false;
    private static final String bshPlatID = "000003"; // 博西PlatID

    /**
     * 获取access_token
     * @return
     */
    private String getAccessToken(String owner) {
        BSHToken record = new BSHToken();
        record.setOwner(owner);
        List<BSHToken> tokens = bshTokenMapper.select(record);
        if (tokens.size() < 1) {
            return null;
        }

        BSHToken bshToken = tokens.get(0);
        String accessToken = bshToken.getAccessToken();
        Boolean isExpired = false;
        if (bshToken.getUpdateTime() != null) {
            isExpired = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - bshToken.getUpdateTime().getTime()) > 86400;
        } else if (bshToken.getCreateTime() != null) {
            isExpired = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - bshToken.getCreateTime().getTime()) > 86400;
        }
        if (accessToken == null || isExpired) { // token不存在或过期
            // 刷新token
            logger.info("TRANSLATOR: accessToken not available refresh token...");
            accessToken = refreshToken(getRefreshToken(owner));
            if (accessToken != null) {
                // 保存token
                logger.info("TRANSLATOR: update accessToken");
                BSHToken updateToken = new BSHToken();
                updateToken.setId(bshToken.getId());
                updateToken.setAccessToken(accessToken);
                updateToken.setUpdateTime(new Date());
                bshTokenMapper.updateByPrimaryKeySelective(updateToken);
            }
        }
        return accessToken;
    }

    /**
     * 获取refresh_token
     */
    private String getRefreshToken(String owner) {
        BSHToken record = new BSHToken();
        record.setOwner(owner);
        List<BSHToken> tokens = bshTokenMapper.select(record);
        if (tokens.size() < 1) {
            return null;
        }

        BSHToken bshToken = tokens.get(0);
        return bshToken.getRefreshToken();
    }

    /**
     * 博西刷新token接口
     */
    private String refreshToken(String refreshToken) {
        String url = host + "/security/oauth/token";
        String param = "grant_type=refresh_token" + "&refresh_token=" + refreshToken;
        HttpRspObject response = HttpUtil.post(url, null, param, "application/x-www-form-urlencoded");
        logAPI(url, param, response);
        JSONObject object = getResponseJSON(response);
        if (object == null) {
            return null;
        }

        String accessToken = object.getString("access_token");
        logger.info("refreshToken get accessToken " + accessToken);
        return accessToken;
    }

    private Map<String, String> getCommonHeaders(String accessToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "*/*");
//        String accessToken = getAccessToken(user);
        if (accessToken != null) {
            headers.put("Authorization", "Bearer " + accessToken);
        }
        headers.put("Content-Type", "application/vnd.bsh.sdk.v1+json");
        return headers;
    }

    private JSONObject getResponseJSON(HttpRspObject response) {
        if (response == null) {
            return null;
        }

        String body = response.getBody();

        // JSON
        JSONObject object = null;
        try {
            object = (JSONObject) JSON.parse(body);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return object;
    }

    private void handleError(JSONObject error) throws Error {
        if (error == null) {
            return;
        }

        String key = error.getString("key");

        if (key.equals("invalid_token")) {
            // 清空token
            bshTokenMapper.clearAccessToken("bsh");
        } else if (key.equals("429")) {
            // 请求限制
//            unblockTime = new Date(new Date().getTime() + 600000);
//            logger.warn("bsh api 429 error set unblockTime " + unblockTime);
//            throw new Error("The rate limit \"10 successive error calls in 10 minutes\" was reached. Requests are blocked.");
        }

        throw new Error(error.toString());
    }

    private void shouldBlockRequest() throws Error {
        return;
//        if (unblockTime == null) {
//            return;
//        }
//        if (new Date().before(unblockTime)) {
//            logger.warn("before unblockTime " + unblockTime);
//            throw new Error("The rate limit \"10 successive error calls in 10 minutes\" was reached. Requests are blocked.");
//        } else {
//            logger.warn("clear unblockTime");
//            unblockTime = null;
//        }
    }

    private void logAPI(String url, String requestBody, HttpRspObject response) {
        if (response == null) {
            logger.info("\nbsh-api {} {}\nnull", url, requestBody);
            return;
        }
        logger.info("\nbsh-api {} {}\n{} {}", url, requestBody, response.getStatusCode(), response.getBody());
    }

    /**
     * 保存设备到数据库
     */
    private void saveDevice(HomeAppliance homeAppliance) {
        Device record = new Device();
        record.setPlatId(bshPlatID);
	logger.info("encoding udeviceId");
	String udeviceId = "";
	try{udeviceId = udeviceIdConvert.encode(homeAppliance.getHaId());}catch(Exception e){logger.info(""+e);}
	logger.info(udeviceId);
        record.setDeviceId(udeviceId);
        Device device;
        try {
            device = deviceMapper.select(record).get(0);
            device.setUpdator("bsh");
            device.setUpdateTime(new Date());
        } catch (Exception e) {
            device = new Device();
            device.setCreator("bsh");
            device.setCreateTime(new Date());
        }
        device.setPlatId(bshPlatID);
        device.setDeviceId(udeviceId);
        device.setName(homeAppliance.getName());
        device.setIsAvail("0");
        device.setDeviceType(homeAppliance.getType());
        if (device.getId() == null) {
            device.setId(GeneratorKit.getID());
            deviceMapper.insertSelective(device);
        } else {
            deviceMapper.updateByPrimaryKeySelective(device);
        }
    }

    /**
     * 查询设备类型
     */
    private BSHDeviceType getDeviceType(String deviceId) {
        Device record = new Device();
        record.setPlatId(bshPlatID);
        record.setDeviceId(deviceId);
        try {
            Device device = deviceMapper.select(record).get(0);
            if (device.getDeviceType().equalsIgnoreCase(BSHDeviceType.FridgeFreezer.toString())) {
                return BSHDeviceType.FridgeFreezer;
            }
            else if (device.getDeviceType().equalsIgnoreCase(BSHDeviceType.Oven.toString())) {
                return BSHDeviceType.Oven;
            }
            else if (device.getDeviceType().equalsIgnoreCase(BSHDeviceType.CoffeeMaker.toString())) {
                return BSHDeviceType.CoffeeMaker;
            }
            else if (device.getDeviceType().equalsIgnoreCase(BSHDeviceType.Dishwasher.toString())) {
                return BSHDeviceType.Dishwasher;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private List<HomeAppliance> bshGetDevices(String token) {
        String url = host + "/api/homeappliances";

        shouldBlockRequest();

        HttpRspObject response = HttpUtil.get(url, getCommonHeaders(token));
        logAPI(url, null, response);

        JSONObject object = getResponseJSON(response);
        if (object == null) {
            return null;
        }

        // data
        JSONObject data = object.getJSONObject("data");
        if (data == null) {
            handleError(object.getJSONObject("error"));
            return null;
        }

        // settings
        JSONArray homeappliances = data.getJSONArray("homeappliances");
        if (homeappliances == null) {
            return null;
        }


        List<HomeAppliance> devices = new ArrayList<>();
        for (Object homeappliance : homeappliances) {
            HomeAppliance device = JSON.toJavaObject((JSONObject)homeappliance, HomeAppliance.class);
	    logger.info("Trying to save device");
            saveDevice(device);
            devices.add(device);
        }

        return devices;
    }

    @Override
    public DeviceListResVO getDevices(String user, String deviceType) {
        String token = getAccessToken(user);
        if (token == null) {
            return new DeviceListResVO(RetObject.tokenError());
        }

        List<HomeAppliance> devices = bshGetDevices(token);
        List<BSHDevice> list = new ArrayList<>();
        for (HomeAppliance device : devices) {
            BSHDeviceInfo info = new BSHDeviceInfo(device);
            if (deviceType != null) {
                if (deviceType.equals(info.getDeviceType())) { // filter device type
                    BSHDevice bshDevice = new BSHDevice();
		    info.setDeviceID(udeviceIdConvert.encode(info.getDeviceID()));
                    bshDevice.setDeviceInfo(info);
                    list.add(bshDevice);
                }
            } else {
                BSHDevice bshDevice = new BSHDevice();
		info.setDeviceID(udeviceIdConvert.encode(info.getDeviceID()));
                bshDevice.setDeviceInfo(info);
                list.add(bshDevice);
            }
        }
        DeviceListResVO result = new DeviceListResVO(list);
        return result;
    }
    private HomeAppliance bshGetDeviceInfo(String token, String deviceID){
	String url = host + "/api/homeappliances/" + deviceID;
	HttpRspObject response = HttpUtil.get(url,getCommonHeaders(token));
	logAPI(url, null ,response);
	JSONObject object = getResponseJSON(response);
	if(object == null){
		return null;
	}
	
	JSONObject data = object.getJSONObject("data");
	if(data == null){
		handleError(object.getJSONObject("error"));
		return null;
	}
//	JSONArray homeappliances = data.getJSONArray("homeappliances");
	//if(homeappliances == null){
	//	return null;
	//}
	//List<HomeAppliance> devices = new ArrayList<>();
	//for (Object homeappliance : data){
	HomeAppliance device = JSON.toJavaObject(data, HomeAppliance.class);
	saveDevice(device);
	
	return device;
	}	

	
    @Override
    public DeviceResVO getDeviceInfo(String user, String deviceID){
	deviceID = udeviceIdConvert.decode(deviceID);
	String token = getAccessToken(user);
	if (token == null){
		return new DeviceResVO(RetObject.tokenError());
			}
	HomeAppliance device = bshGetDeviceInfo(token,deviceID);
	BSHDeviceInfo info = new BSHDeviceInfo(device);
	BSHDevice bshDevice = new BSHDevice();
	info.setDeviceID(deviceID);
	bshDevice.setDeviceInfo(info);
	DeviceResVO result = new DeviceResVO(bshDevice);
	return result;
}
    private List<HomeApplianceStatus> bshGetSettings(String token, String deviceId) throws Error {
        String url = host + "/api/homeappliances/" + deviceId + "/settings";
        shouldBlockRequest();

        HttpRspObject response = HttpUtil.get(url, getCommonHeaders(token));
        logAPI(url, null, response);

        JSONObject object = getResponseJSON(response);
        if (object == null) {
            return null;
        }

        // data
        JSONObject data = object.getJSONObject("data");
        if (data == null) {
            handleError(object.getJSONObject("error"));
            return null;
        }

        // settings
        JSONArray settings = data.getJSONArray("settings");
        if (settings == null) {
            return null;
        }

        List<HomeApplianceStatus> statuses = new ArrayList<>();
        for (Object aSetting : settings) {
            JSONObject setting = (JSONObject) aSetting;
            HomeApplianceStatus homeApplianceStatus = new HomeApplianceStatus(setting.getString("key"), setting.getString("value"));
            statuses.add(homeApplianceStatus);
        }

        return statuses;
    }

    private List<HomeApplianceStatus> bshGetStatus(String token, String deviceId) {
        String url = host + "/api/homeappliances/" + deviceId + "/status";
        shouldBlockRequest();

        HttpRspObject response = HttpUtil.get(url, getCommonHeaders(token));
        logAPI(url, null, response);

        JSONObject object = getResponseJSON(response);
        if (object == null) {
            return null;
        }

        // data
        JSONObject data = object.getJSONObject("data");
        if (data == null) {
            handleError(object.getJSONObject("error"));
            return null;
        }

        // status
        JSONArray statusArray = data.getJSONArray("status");
        if (statusArray == null) {
            return null;
        }

        List<HomeApplianceStatus> statuses = new ArrayList<>();
        for (Object aSetting : statusArray) {
            JSONObject setting = (JSONObject) aSetting;
            HomeApplianceStatus homeApplianceStatus = new HomeApplianceStatus(setting.getString("key"), setting.getString("value"));
            statuses.add(homeApplianceStatus);
        }

        return statuses;
    }

    private HomeApplianceProgram bshGetActiveProgram(String token, String deviceId) {
        String url = host + "/api/homeappliances/" + deviceId + "/programs/active";
        shouldBlockRequest();

        HttpRspObject response = HttpUtil.get(url, getCommonHeaders(token));
        logAPI(url, null, response);

        JSONObject object = getResponseJSON(response);
        if (object == null) {
            return null;
        }

        // data
        JSONObject data = object.getJSONObject("data");
        if (data == null) {
            JSONObject error = object.getJSONObject("error");
            if (error != null && error.get("key").equals("SDK.Error.NoProgramActive")) {
                return null;
            }
            handleError(object.getJSONObject("error"));
            return null;
        }

        String programKey = data.get("key").toString();
        List<HomeApplianceStatus> options = new ArrayList<>();
        JSONArray optionsArray = data.getJSONArray("options");
        for (Object anOption : optionsArray) {
            JSONObject optionObject = (JSONObject) anOption;
            HomeApplianceStatus option = new HomeApplianceStatus(optionObject.getString("key"), optionObject.getString("value"));
            options.add(option);
        }

        HomeApplianceProgram program = new HomeApplianceProgram();
        program.setProgram(programKey);
        program.setOptions(options);

        return program;
    }

    @Override
    public DeviceStatusResVO getDeviceStatus(String user, String deviceId) {
        String token = getAccessToken(user);
        if (token == null) {
            return new DeviceStatusResVO(RetObject.tokenError());
        }
	deviceId = udeviceIdConvert.decode(deviceId);
	if(deviceId == null){
	    return new DeviceStatusResVO(RetObject.deviceNotExist());
	}
	logger.info(deviceId); 
        BSHDeviceType deviceType = getDeviceType(deviceId);
        if (deviceType == null) {
            return new DeviceStatusResVO(RetObject.deviceNotExist());
        }

        List<HomeApplianceStatus> settings = bshGetSettings(token, deviceId);
        List<HomeApplianceStatus> status = bshGetStatus(token, deviceId);
        switch (deviceType) {
            case FridgeFreezer: {
                FridgeFreezerStatus fridgeFreezerStatus = new FridgeFreezerStatus(settings, status);
                BSHDevice device = new BSHDevice();
                device.setDeviceAttr(fridgeFreezerStatus.getModelMap());
                return new DeviceStatusResVO(device);
            }
            case Oven: {
                HomeApplianceProgram program = bshGetActiveProgram(token, deviceId);
                OvenStatus ovenStatus = new OvenStatus(settings, status, program);
                BSHDevice device = new BSHDevice();
                device.setDeviceAttr(ovenStatus.getModelMap());
                return new DeviceStatusResVO(device);
            }
            case Dishwasher: {
                HomeApplianceProgram program = bshGetActiveProgram(token, deviceId);
                DishwasherStatus deviceAttr = new DishwasherStatus(settings, status, program);

                List<HomeAppliance> devices = bshGetDevices(token);
                for (HomeAppliance device : devices) {
                    if (device.getHaId().compareTo(deviceId) == 0) {
                        if (device.getConnected() == true) {
                            deviceAttr.setConnected(BSHDeviceAttr.trueValue);
                        } else {
                            deviceAttr.setConnected(BSHDeviceAttr.falseValue);
                        }
                        break;
                    }
                }

                BSHDevice device = new BSHDevice();
                device.setDeviceAttr(deviceAttr.getModelMap());
                return new DeviceStatusResVO(device);
            }
            case CoffeeMaker: {
                HomeApplianceProgram program = bshGetActiveProgram(token, deviceId);
                CoffeemakerStatus deviceAttr = new CoffeemakerStatus(settings, status, program);
                BSHDevice device = new BSHDevice();
                device.setDeviceAttr(deviceAttr.getModelMap());
                return new DeviceStatusResVO(device);
            }
            default:
                break;
        }
        return new DeviceStatusResVO(RetObject.fail());
    }

    private Boolean bshSetSettings(String token, String deviceId, HomeApplianceStatus status) {
        String url = host + "/api/homeappliances/" + deviceId + "/settings/" + status.getKey();
        JSONObject object = new JSONObject();
        object.put("data", status.toOperationObject());
        String body = object.toJSONString();

        shouldBlockRequest();

        HttpRspObject response = HttpUtil.put(url, getCommonHeaders(token), body, "application/vnd.bsh.sdk.v1+json");
        logAPI(url, body, response);
        if (response != null && response.getStatusCode() == 204) {
            return true;
        }
        JSONObject responseObject = getResponseJSON(response);
        if (responseObject != null) {
            handleError(responseObject.getJSONObject("error"));
        }
        return false;
    }

    private Boolean bshSetProgram(String token, String deviceId, String program, List<HomeApplianceStatus> options) {
        String url = host + "/api/homeappliances/" + deviceId + "/programs/active";
        if (program == null) {
            url = url + "/options";
        }

        JSONArray optionsArray = new JSONArray();
        for (HomeApplianceStatus option : options) {
            optionsArray.add(option.toOperationObject());
        }
        JSONObject dataObject = new JSONObject();
        if (program != null) {
            dataObject.put("key", program);
        }
        dataObject.put("options", optionsArray);
        JSONObject object = new JSONObject();
        object.put("data", dataObject);
        String body = object.toJSONString();

        shouldBlockRequest();

        HttpRspObject response = HttpUtil.put(url, getCommonHeaders(token), body, "application/vnd.bsh.sdk.v1+json");
        logAPI(url, body, response);
        if (response != null && response.getStatusCode() == 204) {
            return true;
        }
        JSONObject responseObject = getResponseJSON(response);
        if (responseObject != null) {
            handleError(responseObject.getJSONObject("error"));
        }
        return false;
    }
   
	 
    @Override
    @Async
    public RetObject dataGateway(String user, String deviceId, String platID){
	try{
		Thread.sleep(2000);
		DeviceStatusResVO result = getDeviceStatus(user,deviceId);
		List<BSHDevice> devices = result.getDevices();
		for(BSHDevice device: devices){
			ModelMap deviceAttr = device.getDeviceAttr();
			BSHDataGateway dataGateway = new BSHDataGateway(deviceId,deviceAttr);
			String url = "";
			if("000011".equals(platID)){
				url = "http://test.kkapp.com/v1/dataservices/dataGateway";}
			else if("000006".equals(platID)){
				url = "http://203.187.186.136:8180/AWEConnectionDemo/v1/dataservices/dataGateway";
			}else if("000003".equals(platID)){
				return RetObject.success();}
			Map<String, String> headers = new HashMap();
			//headers.put("PlatID":"000003");
			//headers.put("AppID":"12345678");
			String body = new ObjectMapper().writeValueAsString(dataGateway);
			logger.info(body);
			HttpRspObject httpRspObject = HttpUtil.post(url,headers,body,"application/json;charset=UTF-8");
			JSONObject httpRspBody = JSONObject.parseObject(httpRspObject.getBody());
			String httpRetCode = httpRspBody.getString("RetCode");
			logger.info("Gateway result is " + httpRetCode + "And " + httpRspBody.getString("RetInfo"));
			RetObject ret = new RetObject();
			//if("000011".equals(platID)){
			//	ret = dataGatewayCoffee(user, deviceId, platID);			
			//1}
			if("200".equals(httpRetCode)){
				return RetObject.success();
			}	
			}
	}catch(Error error){
		return RetObject.fail();
	}catch (Exception exception){
		exception.printStackTrace();
	}
	
	return RetObject.fail();
	}
    @Override
    @Async
    public RetObject dataGatewayCoffee(String user, String deviceId, String platID){
        try{
                Thread.sleep(2000);
                DeviceStatusResVO result = getDeviceStatus(user,deviceId);
                List<BSHDevice> devices = result.getDevices();
                for(BSHDevice device: devices){
                        ModelMap deviceAttr = device.getDeviceAttr();
                        BSHDataGateway dataGateway = new BSHDataGateway(deviceId,deviceAttr);
                        String url = "";
                        if("000011".equals(platID)){
                                url = "http://test.kkapp.com/v1/dataservices/dataGateway";}
                        else if("000006".equals(platID)){
                                url = "http://203.187.186.136:8180/AWEConnectionDemo/v1/dataservices/dataGateway";
                        }else if("000003".equals(platID)){
                                }
                        Map<String, String> headers = new HashMap();
                        //headers.put("PlatID":"000003");
                        //headers.put("AppID":"12345678");
                        String body = new ObjectMapper().writeValueAsString(dataGateway);
                        logger.info(body);
                        HttpRspObject httpRspObject = HttpUtil.post(url,headers,body,"application/json;charset=UTF-8");
                        JSONObject httpRspBody = JSONObject.parseObject(httpRspObject.getBody());
                        String httpRetCode = httpRspBody.getString("RetCode");
                        logger.info("Gateway result is " + httpRetCode + "And " + httpRspBody.getString("RetInfo"));
			//return RetObject.success();
                        }
		Thread.sleep(120000);
		DeviceStatusResVO result2 = getDeviceStatus(user,deviceId);
                List<BSHDevice> devices2 = result2.getDevices();
                for(BSHDevice device2: devices2){
                        ModelMap deviceAttr = device2.getDeviceAttr();
                        BSHDataGateway dataGateway = new BSHDataGateway(deviceId,deviceAttr);
                        String url = "";
                        if("000011".equals(platID)){
                                url = "http://test.kkapp.com/v1/dataservices/dataGateway";}
                        else if("000006".equals(platID)){
                                url = "http://203.187.186.136:8180/AWEConnectionDemo/v1/dataservices/dataGateway";
                        }else if("000003".equals(platID)){
                                }
                        Map<String, String> headers = new HashMap();
                        //headers.put("PlatID":"000003");
                        //headers.put("AppID":"12345678");
                        String body = new ObjectMapper().writeValueAsString(dataGateway);
                        logger.info(body);
                        HttpRspObject httpRspObject = HttpUtil.post(url,headers,body,"application/json;charset=UTF-8");
                        JSONObject httpRspBody = JSONObject.parseObject(httpRspObject.getBody());
                        String httpRetCode = httpRspBody.getString("RetCode");
                        logger.info("Gateway result is " + httpRetCode + "And " + httpRspBody.getString("RetInfo"));
                        return RetObject.success();
                        }
        }catch(Error error){
                return RetObject.fail();
        }catch (Exception exception){
                exception.printStackTrace();
        }
	return RetObject.fail();
        }
    @Override
    public RetObject operateDevice(String user, String deviceId, ModelMap attr) {
        String token = getAccessToken(user);
        if (token == null) {
            return RetObject.tokenError();
        }
	deviceId = udeviceIdConvert.decode(deviceId);
	if(deviceId == null)
	{
		return RetObject.deviceNotExist();
	}
        BSHDeviceType deviceType = getDeviceType(deviceId);

	logger.info (""+deviceType);
        if (deviceType == null) {
            return RetObject.deviceNotExist();
        }

        HomeApplianceStatus setting = null;
        switch (deviceType) {
        /*    case FridgeFreezer: {
                setting = FridgeFreezerStatus.parseOperation(attr);
                Boolean result = bshSetSettings(token, deviceId, setting);
                if (result) {
                    return RetObject.success();
                }
                return RetObject.fail();
            }*/

            case Oven: {
                setting = OvenStatus.parseCommonSetting(attr);
                break;
            }

            /*case Dishwasher: {
                setting = DishwasherStatus.parseCommonSetting(attr);
                break;
            }*/
            case CoffeeMaker: {
                setting = CoffeemakerStatus.parseCommonSetting(attr);
                break;
            }

            default:
                return RetObject.fail();
        }

        if (setting != null) {
            Boolean result = bshSetSettings(token, deviceId, setting);
            if (result) {
                return RetObject.success();
            }
	    logger.info("setting is null");
            return RetObject.fail();
        }

        // set program (temperature, duration)
        switch (deviceType) {
            case Oven:
                if (attr.get("program") != null) {
                    String programKey = OvenStatus.parseProgramKey(attr);
                    if (programKey == null) {
                        return RetObject.fail();
                    }
                    List<HomeApplianceStatus> options = OvenStatus.parseProgramOptions(attr);
                    Boolean result = bshSetProgram(token, deviceId, programKey, options);
                    if (result) {
                        return RetObject.success();
                    }
                    return RetObject.fail();
                }/* else { // only set option
                    List<HomeApplianceStatus> options = OvenStatus.parseProgramOptions(attr);
                    if (options.size() == 0) {
                        return RetObject.fail();
                    }
                    Boolean result = bshSetProgram(token, deviceId, null, options);
                    if (result) {
                        return RetObject.success();
                    }
                    return RetObject.fail();
                }

            case Dishwasher:
                if (attr.get("program") != null) {
                    String programKey = DishwasherStatus.parseProgramKey(attr);
                    if (programKey == null) {
                        return RetObject.fail();
                    }
                    List<HomeApplianceStatus> options = new ArrayList<>();
                    Boolean result = bshSetProgram(token, deviceId, programKey, options);
                    if (result) {
                        return RetObject.success();
                    }
                    return RetObject.fail();
                }
                break;*/
            case CoffeeMaker:
                if (attr.get("program") != null) {
                    String programKey = CoffeemakerStatus.parseProgramKey(attr);
	            logger.info(programKey);
                    if (programKey == null) {
                        return RetObject.fail();
                    }
                    List<HomeApplianceStatus> options = new ArrayList<>();
                    Boolean result = bshSetProgram(token, deviceId, programKey, options);
                    if (result) {
                        return RetObject.success();
                    }
                    return RetObject.fail();
                }
                break;
            default:
                return RetObject.fail();
        }
        return RetObject.fail();
    }

    @Override
    public Boolean isPlatIdSupport(String platId) {
        if (    "000003".equals(platId) ||
                "000004".equals(platId) ||
                "000005".equals(platId) ||
                "000006".equals(platId) ||
                "000007".equals(platId) ||
                "000008".equals(platId) ||
                "000009".equals(platId) ||
		"000010".equals(platId) ||
		"000011".equals(platId) ||
		"000012".equals(platId)) {
            return true;
        }
        return false;
    }

}
