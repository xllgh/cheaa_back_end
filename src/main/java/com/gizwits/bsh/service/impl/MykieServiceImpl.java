package com.gizwits.bsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.*;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.bean.resvo.DeviceStatusResVO;
import com.gizwits.bsh.bean.reqvo.*;
import com.gizwits.bsh.mapper.BSHTokenMapper;
import com.gizwits.bsh.mapper.DeviceMapper;
import com.gizwits.bsh.model.entity.BSHToken;
import com.gizwits.bsh.model.entity.Device;
import com.gizwits.bsh.bean.BSHDevice;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.service.MykieService;
import com.gizwits.bsh.util.GeneratorKit;
import com.gizwits.bsh.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import com.gizwits.domain.opBean.RsqBasicOP;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class MykieServiceImpl implements MykieService{

	private static final Logger logger = LoggerFactory.getLogger(MykieServiceImpl.class);

	@Autowired
	DeviceMapper deviceMapper;

	private static final String MideaHost = "http://openapi.midea.com:8888";
	private static final String MeilinHost = "http://118.178.85.67:7575";
	private static final String contentType = "application/json;charset=UTF-8";
	
	private Map<String, String> setCommonHeaders(String targetPlat){
		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "*/*");
		//headers.put();
		return headers;
	}
		
	private JSONObject loginMeiling(){
		Map<String, String> headers = new HashMap<>();
		//headers.put("Content-type", "application/json");
		headers.put("PlatID", "000003");
		headers.put("AuthToken", "poweroverwhelming");
		String body = "{\"User\":[{\"UserBase\":{\"loginID\":\"46382834623\"}}]}";
		String url = MeilinHost + "/v1/user/login";
		String contenttype = "application/json";
		HttpRspObject httpResObject = HttpUtil.post(url,headers,body,contenttype);
		JSONObject response = getResponseJSON(httpResObject);
		logger.info(response.toString());
		return response;
	}

	private String identifyPlat(String targetPlat){
		String PlatName = new String();
		PlatName = "default";
		if("000003".equals(targetPlat)){
			PlatName = "bsh";
		}else if("000008".equals(targetPlat)){
			PlatName = "Midea";
		}else{
			PlatName = "Meilin";
		}
		return PlatName ;
	}
	
	private void logAPI(String url, String requestBody, HttpRspObject response) {
        	if (response == null) {
            		logger.info("\nbsh-api {} {}\nnull", url, requestBody);
            		return;
        	}
        	logger.info("\nbsh-api {} {}\n{} {}", url, requestBody, response.getStatusCode(), response.getBody());
    	}

	private String targetPlatUrl(String targetPlat){
		if("000008".equals(targetPlat)){
			return MideaHost;
		}else if("000004".equals(targetPlat)){
			return MeilinHost;
		}else if("000003".equals(targetPlat)){
			return "http://52.80.27.61:8080";
		}
		return null;
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

    /**
     * 保存设备到数据库
     */
    private void saveDevice(HomeAppliance homeAppliance, String targetPlat) {
        Device record = new Device();
        record.setPlatId(targetPlat);
	String PlatName = new String();
	PlatName = identifyPlat(targetPlat);
        record.setDeviceId(homeAppliance.getHaId());
        Device device;
        try {
            device = deviceMapper.select(record).get(0);
            device.setUpdator(PlatName);
            device.setUpdateTime(new Date());
        } catch (Exception e) {
            device = new Device();
            device.setCreator(PlatName);
            device.setCreateTime(new Date());
        }
        device.setPlatId(targetPlat);
        device.setDeviceId(homeAppliance.getHaId());
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
	private String setListBody(String user, String deviceType){
		String body = new String();
		if(deviceType == null){
			body = "{\"UserID\":\""+user+"\"}";
		}else{
			body = "{\"UserID\":\""+user+"\",\"Device\":[{\"DeviceInfo\":{\"deviceType\":\""+deviceType+"\"}}]}";
		}
		logger.info(body);
		return body;
}	

	
    	private List<BSHDevice> getDevices(String user, String deviceType, String targetPlat){ 
		String url = targetPlatUrl(targetPlat) + "/v1/users/devices";
		String bodytext =setListBody(user,deviceType);
		Map<String, String> headers = new HashMap();
		headers.put("PlatID","000003");
		headers.put("AppID","123456787");
		logger.info(url+ " "+ bodytext + " "+ contentType);
		HttpRspObject response = HttpUtil.post(url,headers,bodytext,contentType);
		logAPI(url, null, response);
		JSONObject responseJson = getResponseJSON(response);
		if(responseJson == null){return null;}
		JSONArray homeappliances = responseJson.getJSONArray("Device");
		logger.info(""+homeappliances.toString());
		if(homeappliances == null){return null;}
		List<BSHDevice> devices = new ArrayList<>();
		for (Object homeappliance : homeappliances){
			logger.info("Every object is" + homeappliance.toString());
			BSHDevice device = JSON.parseObject(homeappliance.toString(),BSHDevice.class);
			devices.add(device);
		}
		return devices;
}


	@Override
	public DeviceListResVO getDevices(String user, String deviceType){
		List<BSHDevice> MeilingList = getDevices("2",deviceType,"000004");
		List<BSHDevice> MideaList = getDevices("a16b2d9855df4bb488f73113327fffff",deviceType,"000008");
		MeilingList.addAll(MideaList);
		DeviceListResVO result = new DeviceListResVO(MeilingList);
		return result;
}

	private String setStatusBody(String deviceId, String targetPlat){
		String body = new String();
		String user = new String();
		if("000004".equals(targetPlat)){user="2";}
		else if("000008".equals(targetPlat)){user= "a16b2d9855df4bb488f73113327fffff";}
		body = "{\"UserID\":\"" + user + "\",\"Device\":[{\"DeviceInfo\":{\"deviceID\":\"" + deviceId + "\"}}]}";
		logger.info(body);
		return body;
	
	
	}
	
	@Override
	public JSONObject getDeviceStatus(String user, String deviceId, String targetPlat){
		String url = targetPlatUrl(targetPlat) + "/v1/devices/deviceStatus";
		//JSONObject accesstoken = loginMeiling();
		String bodytext = setStatusBody(deviceId,targetPlat);
		Map<String, String> headers = new HashMap();
		headers.put("PlatID","000003");
		headers.put("AppID","12345678");
		logger.info(url+ " " + bodytext + " " +contentType);
		HttpRspObject response = HttpUtil.post(url,headers,bodytext,contentType);
		logAPI(url, null, response);
		JSONObject responseJson = getResponseJSON(response);
		return responseJson;
	}
	
	private String setOperateBody(RsqBasicOP rsqBasicOP,String targetPlat){
		String body = new String();
		String userId = new String();
		if("000003".equals(targetPlat)){
			userId = "cheaashowcase2018@mailinator.com";
		}else if("000004".equals(targetPlat)){
			userId = "2";}
		else if("000008".equals(targetPlat)){
			userId = "a16b2d9855df4bb488f73113327fffff";
		}
			
		body = "{\"UserID\":\"" + userId + "\",\"Device\":[{\"DeviceInfo\":{\"deviceID\":\"" + rsqBasicOP.getDeviceID() + "\"},\"DeviceAttr\":" + rsqBasicOP.getDeviceAttr().toString() + "}]}";
		//return body;}
		//body = "{\"Device\":[{\"DeviceInfo\":{\"deviceID\":\"" + rsqBasicOP.getDeviceID() + "\"}\"DeviceAttr\":" + rsqBasicOP.getDeviceAttr().toString() + "}]}"; 
		logger.info(body);
		return body;
		}
	
	@Override
	public JSONObject operateDevice(RsqBasicOP rsqBasicOP,String targetPlat){
		String url = targetPlatUrl(targetPlat)+"/v1/devices/op";
		logger.info(url);
		String body = setOperateBody(rsqBasicOP,targetPlat);
		Map<String, String> headers = new HashMap();
		headers.put("PlatID","000003");
		if("000004".equals(targetPlat)){headers.put("AccessToken","173436cb-f52c-41a5-91af-17aacfb2b51d");}
		else if("000008".equals(targetPlat)){headers.put("AccessToken","5034795b42494a4d84cc33a1074579a3");}
		HttpRspObject response = HttpUtil.post(url,headers,body,contentType);
		logAPI(url, null ,response);
		JSONObject responseJson = getResponseJSON(response);
		return responseJson;
	}
}
