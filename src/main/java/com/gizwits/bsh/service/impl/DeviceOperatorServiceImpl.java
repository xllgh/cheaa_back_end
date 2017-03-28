package com.gizwits.bsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.HttpRspObject;
import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.setting.EnvSetting;
import com.gizwits.bsh.bean.setting.TokenSetting;
import com.gizwits.bsh.common.config.SysConsts;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.enums.MultiCloudHost;
import com.gizwits.bsh.mapper.DeviceMapper;
import com.gizwits.bsh.mapper.DeviceTypeMapper;
import com.gizwits.bsh.model.entity.Device;
import com.gizwits.bsh.model.entity.DeviceType;
import com.gizwits.bsh.service.DeviceOperatorService;
import com.gizwits.bsh.util.DeviceAttributeUtil;
import com.gizwits.bsh.util.HttpUtil;
import com.gizwits.domain.opBean.RsqBasicOP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/11/21.
 */
@Service(value = "deviceOperatorService")
public class DeviceOperatorServiceImpl extends BaseServiceImpl<Device,Long> implements DeviceOperatorService {
    private static final Logger logger = LoggerFactory.getLogger(DeviceOperatorServiceImpl.class);

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceTypeMapper deviceTypeMapper;
    /**
     *加载所有的DeviceType
     * @return
     */
    @Override
    public ResultMap loadAllDeviceType() {
        return new ResultMap(ErrType.SUCCESS,deviceTypeMapper.selectAllDeviceType());
    }

    /**
     * 查询指定DeviceType的名字
     * @param typeCode
     * @return
     */
    @Override
    public ResultMap getDeviceTypeAndPlatId(String typeCode,String platId) {
        if(deviceTypeMapper.selectByDeviceCodeAndPlatId(new DeviceType(platId,typeCode))==null){
            return new ResultMap(ErrType.FAIL,"设备类型["+typeCode+"]不存在");
        }else{
            return new ResultMap(ErrType.SUCCESS,deviceTypeMapper.selectByDeviceCodeAndPlatId(new DeviceType(platId,typeCode)));
        }
    }

    /**
     * 处理操作设备请求
     * @param rsqBasicOP
     * @return
     */
    @Override
    public ResultMap handleOperatorRequest(RsqBasicOP rsqBasicOP){
        Device device =  deviceMapper.selectByDeviceId(rsqBasicOP.getDeviceID());
        if(device==null){
            return new ResultMap(ErrType.DEVICE_NOTEXISTED);
        }
        return handleRequest(device,rsqBasicOP);
    }

    /**
     * 处理设备状态查询请求
     * @param deviceId
     * @return
     */
    @Override
     public ResultMap handleDeviceStatusRequest(String deviceId){
         Device device =  deviceMapper.selectByDeviceId(deviceId);
         if(device==null){
             return new ResultMap(ErrType.DEVICE_NOTEXISTED);
         }
         return handleRequest(device,null);
     }

     private ResultMap handleRequest(Device device,RsqBasicOP rsqBasicOP){
         try{
             HttpRspObject httpRspObject = null;
             if(rsqBasicOP!=null){//设备操作

                 httpRspObject = HttpUtil.post(getUrl(device,"OPERATOR"),getHeaders(device),
                         getOperatorParameters(rsqBasicOP,device),getContentTypeWithJSON());
             }else{//设备状态查询

                 httpRspObject = HttpUtil.post(getUrl(device,"STATUS"),getHeaders(device),
                         getStatusParameter(device),getContentTypeWithJSON());
             }
             return dealHttpRspObject(httpRspObject,device,rsqBasicOP!=null);
         }catch (Exception ex){
             ex.printStackTrace();
             return new ResultMap(ErrType.SYSTEM_ERROR);
         }
     }

    public Map<String,String> getHeaders(Device device){
        Map<String,String> headers = new HashMap<>();
        headers.put("PlatID",SysConsts.BSH_PLATID);
        headers.put("AppID",device.getAppId());
        if(device.getAccessToken()!=null){
            headers.put("AccessToken",device.getAccessToken());
        }
        return headers;
    }

    /**
     * 返回设备操作Body
     * @param rsqBasicOP
     * @return
     */
    private String getOperatorParameters(RsqBasicOP rsqBasicOP,Device device){

        JSONObject transedJson = DeviceAttributeUtil.switchDeviceAttribute2Satisfy(device.getPlatId(),device.getDeviceType(),rsqBasicOP.getDeviceAttr(),true);

        String result = "{\"UserID\":\"%s\"," +
                         "\"Device\":" +
                              "[{\"DeviceInfo\":{" +
                                   "\"deviceID\": \"%s\"," +
                                   "\"deviceType\":\"%s\"}," +
                                "\"DeviceAttr\":%s}]}";
        return String.format(result,device.getOwnerId(),
                                            rsqBasicOP.getDeviceID(),
                                            device.getDeviceType(),
                                        transedJson.toJSONString());
    }

    /**
     * 美的的空调设备需要属性进行适配
     * @param rsqBasicOP
     * @param device
     */
    private void adapterMeidiOperator(RsqBasicOP rsqBasicOP,Device device){

    }

    /**
     * 返回设备状态查询Body
     * @param device
     * @return
     */
    private String getStatusParameter(Device device){
        String result = "{\"UserID\":\"%s\"," +
                         "\"Device\":[{" +
                            "\"DeviceInfo\":{" +
                                "\"deviceID\":\"%s\"," +
                                "\"deviceType\":\"%s\"}}]}";
        return String.format(result,device.getOwnerId(),
                                            device.getDeviceId(),
                                            device.getDeviceType());
    }

    private String getContentTypeWithJSON(){
        return "application/json;charset=UTF-8";
    }

    /**
     * 根据类型返回相应的URL
     * @param device
     * @param type 选项 OPERATOR、STATUS
     * @return
     */
    public String getUrl(Device device,String type){
        if(type.equalsIgnoreCase("OPERATOR")){
            return getOperatorUrl(device);
        }else if(type.equalsIgnoreCase("STATUS")){
            return getStatusUrl(device);
        }else{
            return null;
        }
    }

    private String getOperatorUrl(Device device){
        if(device.getFactory().equalsIgnoreCase("HAIER")){
            return SysConsts.HOST_HAIER_DEVICE_OPERATOR;
        }else if(device.getFactory().equalsIgnoreCase("HAIXIN")){
            return SysConsts.HOST_HAIXIN_DEVICE_OPERATOR;
        }else if(device.getFactory().equalsIgnoreCase("MEIDI")){
            return SysConsts.HOST_MEIDI_DEVICE_OPERATOR;
        }else{
            return null;
        }
    }

    private String getStatusUrl(Device device){
        if(device.getFactory().equalsIgnoreCase("HAIER")){
            return SysConsts.HOST_HAIER_DEVICE_STATUS;
        }else if(device.getFactory().equalsIgnoreCase("HAIXIN")){
            return SysConsts.HOST_HAIXIN_DEVICE_STATUS;
        }else if(device.getFactory().equalsIgnoreCase("MEIDI")) {
            return SysConsts.HOST_MEIDI_DEVICE_STATUS;
        }else{
            return null;
        }
    }

    private ResultMap dealHttpRspObject(HttpRspObject httpRspObject,Device device,boolean isHandleDevice){
        ResultMap result = new ResultMap();
        if(httpRspObject.getStatusCode()!=200){
            return new ResultMap(ErrType.SYSTEM_ERROR);
        }
        if(httpRspObject.getStatusCode() == 200) {
            result.setFlag(true);
            result.setMsg("请求成功");
            if(!isHandleDevice){//设备状态查询
                if(JSON.parseObject(httpRspObject.getBody()).containsKey("Device")&&JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").size()>0){
                    JSONObject gotJson = JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").getJSONObject(0);
                    //返回结果DeviceAttr进行转换,覆盖旧值
                    gotJson.put("DeviceAttr",DeviceAttributeUtil.switchDeviceAttribute2Satisfy(device.getPlatId(),device.getDeviceType(),gotJson.getJSONObject("DeviceAttr"),false));
                    //gotJson.put("DeviceInfo",gotJson.getJSONObject("DeviceInfo").put("imageUri",device.getImageUri()));
                    putImageUriToResult(gotJson,device);
                    result.setData(gotJson);
                }else{
                    JSONObject object = new JSONObject();
                    object.put("imageUri",device.getImageUri());
                    object.put("deviceName",device.getName());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("DeviceInfo",object);
                    result.setData(jsonObject);
                }
            }else{//设备操作
                result.setData(JSON.parseObject(httpRspObject.getBody()));
            }
        }else{
            JSONObject object = new JSONObject();
            object.put("imageUri",device.getImageUri());
            object.put("deviceName",device.getName());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DeviceInfo",object);
            result.setData(jsonObject);
            result.setFlag(false);
            result.setMsg(JSON.parseObject(httpRspObject.getBody()).getString("RetInfo"));
        }
        return result;
    }

    private void putImageUriToResult(JSONObject result,Device device){
        JSONObject object = new JSONObject();
        if(result.containsKey("DeviceInfo")){
            object = result.getJSONObject("DeviceInfo");
        }
        object.put("imageUri",device.getImageUri());
        object.put("deviceName",device.getName());
        result.put("DeviceInfo",object);
    }



    @Override
    public ResultMap getDeviceType(String typeCode) {
        if(deviceTypeMapper.selectByDeviceCode(typeCode)==null){
            return new ResultMap(ErrType.FAIL,"设备类型["+typeCode+"]不存在");
        }else{
            return new ResultMap(ErrType.SUCCESS,deviceTypeMapper.selectByDeviceCode(typeCode));
        }
    }

    private ResultMap dealHttpRspObject(HttpRspObject httpRspObject){
        ResultMap result = new ResultMap();
        if(httpRspObject.getStatusCode()!=200){
            return new ResultMap(ErrType.SYSTEM_ERROR);
        }
        if(JSON.parseObject(httpRspObject.getBody()).getIntValue("RetCode") == 200) {
            result.setFlag(true);
            result.setMsg("请求成功");
            if(JSON.parseObject(httpRspObject.getBody()).containsKey("Device")){//设备状态查询
                if(JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").size()>0){
                    result.setData(JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").getJSONObject(0));
                }
            }else{//设备操作
                result.setData(JSON.parseObject(httpRspObject.getBody()));
            }
        }else{
            result.setFlag(false);
            result.setMsg(JSON.parseObject(httpRspObject.getBody()).getString("RetInfo"));
        }
        return result;
    }


    @Override
    @Deprecated
    public ResultMap handleOperatorCommand(RsqBasicOP rsqBasicOP) {

        ResultMap resultMap = new ResultMap();

        if (rsqBasicOP != null && !StringUtils.isEmpty(rsqBasicOP.getDeviceID()) ) {

//            String devType = DeviceCache.DeviceCacheMap.get(rsqBasicOP.getDeviceID()).getString("DeviceType");
//            BasicOP basicOP = JSON.parseObject(rsqBasicOP.getDeviceAttr().toString(), BasicOP.class);
            String DeviceID = rsqBasicOP.getDeviceID();
            if (DeviceID.equalsIgnoreCase("0007A8B4F71B")) {//海尔设备
                MultiCloudHost cloudHost = MultiCloudHost.HAIER;
                String host = cloudHost.getHost();
                Map<String, String> headers = new HashMap<>();

                headers.put("PlatID", "000006");
                headers.put("AppID", "MB-SN-0000");
                headers.put("AccessToken", "TGT1GTAHOK5LDJTF27A2LFE0C5WOG0");
                headers.put("Content-Type", "application/json");

                String DeviceAttr = rsqBasicOP.getDeviceAttr().toJSONString();
                String deviceIDArrStr = "[{\"DeviceInfo\":{\"deviceID\": \"" + DeviceID + "\", " +
                        "\"deviceType\":\"03001012\"},\"DeviceAttr\":" + DeviceAttr + "}]";
                String param = "{\"Device\":" + deviceIDArrStr + "}";

                try {
                    HttpRspObject httpRspObject = HttpUtil.post("http://" + host + "/AWEConnectionDemo/devices/op", headers, param, "application/json");
                    if (JSON.parseObject(httpRspObject.getBody()).getIntValue("RetCode") == 200) {

                        logger.info("操作状态OK, host:{} , {}", cloudHost, httpRspObject.getBody());
                        resultMap.setFlag(true);
                        resultMap.setMsg("请求成功");
                        resultMap.setData(JSON.parseObject(httpRspObject.getBody()));
                        return resultMap;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("操作状态Fail :{}", host);
                }

            } else {

                MultiCloudHost cloudHost = MultiCloudHost.HAIXIN;
                String host = cloudHost.getHost();
                Map<String, String> headers = new HashMap<>();

                headers.put("PlatID", EnvSetting.PlatID);
                headers.put("AppID", EnvSetting.AppID);
                headers.put("AccessToken", TokenSetting.getTokenStr(cloudHost));
                headers.put("Content-Type", "application/json");

                String deviceIDArrStr = "[{\"DeviceInfo\":{\"deviceID\": \"" + rsqBasicOP.getDeviceID() + "\"}," +
                        "\"DeviceAttr\":" + rsqBasicOP.getDeviceAttr().toString() + "}]";
                String param = "{\"UserID\":\"" + TokenSetting.getUserID(cloudHost) + "\",\"Device\":" + deviceIDArrStr+"}";
                logger.info("param=" + param);
                try {
                    HttpRspObject httpRspObject = HttpUtil.post("http://" + host + "/1.1/devices/op/", headers, param, "application/json");
                    if (JSON.parseObject(httpRspObject.getBody()).getIntValue("RetCode") == 200) {

                        logger.info("操作状态OK, host:{} , {}", cloudHost, httpRspObject.getBody());
                        resultMap.setFlag(true);
                        resultMap.setMsg("请求成功");
                        resultMap.setData(JSON.parseObject(httpRspObject.getBody()));
                        return resultMap;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("操作状态Fail:{}", host);
                }

            }
        }
        resultMap.setFlag(false);
        resultMap.setMsg("系统异常");
        return resultMap;
    }

    /**
     * 获取设备的在线状态
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public void fetchDevicesOnlineStatus() {
        logger.debug("fetchDevicesOnlineStatus");

        List<Device> devices = deviceMapper.selectDevices();
        for(Device device : devices) {
            try {
                HttpRspObject httpRspObject = HttpUtil.post(getStatusUrl(device),getHeaders(device),getStatusParameter(device),getContentTypeWithJSON());
                logger.debug("deviceId:" + device.getDeviceId() +
                        "\ndeviceType:" + device.getDeviceType() +
                        "\nplatId:" + device.getPlatId() +
                        "\nstatus:" + httpRspObject.getStatusCode() + " " + httpRspObject.getBody());
                Boolean online = false;
                if (httpRspObject.getStatusCode() == 200) {
                    JSONObject json = JSON.parseObject(httpRspObject.getBody());
                    if (device.getFactory().equalsIgnoreCase("HAIER")) {
                        JSONArray jsonDevices = json.getJSONArray("Device");
                        if (jsonDevices != null && jsonDevices.size() > 0) {
                            JSONObject jsonDeviceAttr = jsonDevices.getJSONObject(0).getJSONObject("DeviceAttr");
                            if (jsonDeviceAttr != null) {
                                if ("True".equalsIgnoreCase(jsonDeviceAttr.getString("onLine"))) {
                                    online = true;
                                }
                            }
                        }
                    } else if (device.getFactory().equalsIgnoreCase("HAIXIN")) {
                        JSONArray jsonDevices = json.getJSONArray("Device");
                        if (jsonDevices != null && jsonDevices.size() > 0) {
                            if ("True".equalsIgnoreCase(jsonDevices.getJSONObject(0).getString("DeviceOnline"))) {
                                online = true;
                            }
                        }
                    } else if (device.getFactory().equalsIgnoreCase("MEIDI")) {
                        JSONArray jsonDevices = json.getJSONArray("Device");
                        if (jsonDevices != null && jsonDevices.size() > 0) {
                            online = true;
                        }
                    }
                }

                Device updateDevice = new Device();
                updateDevice.setId(device.getId());
                updateDevice.setOnline(online);
                updateDevice.setUpdateTime(new Date());
                deviceMapper.updateByPrimaryKeySelective(updateDevice);

            } catch (Exception e) {
                logger.error("fetchDevicesOnlineStatus throw exception deviceId" + device.getDeviceId());
                e.printStackTrace();
            }
        }
    }

}
