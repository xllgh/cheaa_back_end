package com.gizwits.bsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.HttpRspObject;
import com.gizwits.bsh.enums.MultiCloudHost;
import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.setting.DeviceCache;
import com.gizwits.bsh.bean.setting.EnvSetting;
import com.gizwits.bsh.bean.setting.TokenSetting;
import com.gizwits.bsh.service.QueryService;
import com.gizwits.domain.Device;
import com.gizwits.bsh.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by neil on 2016/11/6.
 */
@Service
public class QueryServiceImpl implements QueryService {

    private static Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

    @Override
    public ResultMap queryDevices(String username, String type) {
        ResultMap resultMap = new ResultMap();

//        for (MultiCloudHost cloudHost : MultiCloudHost.values()) {
        MultiCloudHost cloudHost = MultiCloudHost.HAIXIN;
        String host = cloudHost.getHost();
        Map<String, String> headers = new HashMap<>();

        headers.put("PlatID", EnvSetting.PlatID);
        headers.put("AppID", EnvSetting.AppID);
        headers.put("AccessToken", TokenSetting.getTokenStr(cloudHost));
        headers.put("Content-Type", "application/x-www-form-urlencoded");

        String param = "{UserID=" + TokenSetting.getUserID(cloudHost)+"}";

        try {
            HttpRspObject httpRspObject = HttpUtil.post("http://" + host + "/1.0/user/devices", headers, param, "application/json");
            if (JSON.parseObject(httpRspObject.getBody()).getIntValue("RetCode") == 200) {

                logger.info("获取云设备列表OK, host:{} , {}", cloudHost);
                resultMap.setFlag(true);
                resultMap.setMsg("请求成功");
                resultMap.setData(filterRespJSON(httpRspObject, type));

                //缓存设备列表信息
                handleDeviceArrayCache(filterRespJSON(httpRspObject, type).getJSONArray("Devices"));

                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("第三方登录验证出错host:{}", host);
        }

        resultMap.setFlag(false);
        resultMap.setMsg("系统异常");
        return resultMap;
    }

    @Override
    public ResultMap queryDeviceStatus(String DeviceID) {
        ResultMap resultMap = new ResultMap();

        if (!StringUtils.isEmpty(DeviceID)) {
            MultiCloudHost cloudHost = null;
            if (DeviceID.equalsIgnoreCase("0007A8B4F71B")) {//海尔设备
                cloudHost = MultiCloudHost.HAIER;
                String host = cloudHost.getHost();
                Map<String, String> headers = new HashMap<>();

                headers.put("PlatID", "000006");
                headers.put("AppID", "MB-SN-0000");
                headers.put("AccessToken", "TGT1GTAHOK5LDJTF27A2LFE0C5WOG0");
                headers.put("Content-Type", "application/json");

                String deviceIDArrStr = "[{\"DeviceInfo\":{\"deviceID\": \"" + DeviceID + "\",\"deviceType\":\"03001012\"}}]";
                String param = "{\"Device\":" + deviceIDArrStr+"}";

                try {
                    HttpRspObject httpRspObject = HttpUtil.post("http://" + host + "/AWEConnectionDemo/devices/deviceStatus/", headers, param, "application/json");
                    if (JSON.parseObject(httpRspObject.getBody()).getIntValue("RetCode") == 200) {

                        logger.info("获取云设备状态OK, host:{} , {}", cloudHost);
                        resultMap.setFlag(true);
                        resultMap.setMsg("请求成功");
                        if(JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device")!=null&&JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").size()>0){
                            resultMap.setData(JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").getJSONObject(0));//todo
                        }else{
                            resultMap.setData(null);//todo
                        }

                        return resultMap;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("第三方登录验证出错host:{}", host);
                }

            } else {
                cloudHost = MultiCloudHost.HAIXIN;
                String host = cloudHost.getHost();
                Map<String, String> headers = new HashMap<>();

                headers.put("PlatID", EnvSetting.PlatID);
                headers.put("AppID", EnvSetting.AppID);
                headers.put("AccessToken", TokenSetting.getTokenStr(cloudHost));
                headers.put("Content-Type", "application/json");

                String deviceIDArrStr = "[{\"DeviceInfo\":{\"deviceID\":\"" + DeviceID + "\"}}]";
                String param = "{\"UserID\":\"" + TokenSetting.getUserID(cloudHost) + "\",\"Device\":"+deviceIDArrStr+"}";

                try {
                    HttpRspObject httpRspObject = HttpUtil.post("http://" + host + "/1.1/devices/deviceStatus/", headers, param, "application/json");
                    if (JSON.parseObject(httpRspObject.getBody()).getIntValue("RetCode") == 200) {

                        logger.info("获取云设备状态OK, host:{} , {}", cloudHost);
                        resultMap.setFlag(true);
                        resultMap.setMsg("请求成功");
                        if(JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device")!=null&&JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").size()>0){
                            resultMap.setData(JSON.parseObject(httpRspObject.getBody()).getJSONArray("Device").getJSONObject(0));//todo
                        }else{
                            resultMap.setData(null);//todo
                        }
                        return resultMap;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("第三方登录验证出错host:{}", host);
                }
            }

        }
        resultMap.setFlag(false);
        resultMap.setMsg("系统异常");
        return resultMap;
    }

    //JSONObject属性首字母大写
    public JSONObject switchCaseCamel(JSONObject jsonObject){
        JSONObject object = jsonObject.getJSONObject("DeviceAttr");
        Set<String> set =  object.keySet();
        JSONObject rstObject = new JSONObject();
        Iterator iter = set.iterator();
        while(iter.hasNext()){
            String tmp = iter.next().toString();
            StringBuilder builder = new StringBuilder(tmp);
            builder.setCharAt(0,Character.toUpperCase(builder.charAt(0)));
            rstObject.put(builder.toString(),object.getString(tmp));

        }
        jsonObject.put("DeviceAttr",rstObject);
        return jsonObject;
    }

    public static void main (String args[]){
        String s = "{\"Device\": [{\"DeviceAttr\": {\"onOffStatus\": \"True\", \"lightLevel\": \"1\", \"windLevel\": \"1\", \"mode\": \"1\"}}], \"RetCode\": \"200\", \"RetInfo\": \"ok\"}";

        System.out.println((new StringBuilder()).append(Character.toLowerCase(s.charAt(0))));
    }

    private JSONObject filterRespJSON(HttpRspObject httpRspObject, String type) {

        JSONObject returnJSON = new JSONObject();
        JSONArray returnArr = new JSONArray();
        final JSONArray devices = JSON.parseObject(httpRspObject.getBody()).getJSONArray("Devices");

        if (devices != null && devices.size() > 0) {
            for (int i = 0; i < devices.size(); i++) {
                final JSONObject jsonObject = devices.getJSONObject(i);
                if (StringUtils.isEmpty(type)) {
                    returnArr.add(jsonObject);
                } else {
                    if (type.equalsIgnoreCase(jsonObject.getString("DeviceType"))) {
                        returnArr.add(jsonObject);
                    }
                }
            }
        }
        //todo
        Device device = new Device();
        device.setDeviceAttrs(new JSONObject());
        device.setDeviceID("0007A8B4F71B");
        device.setDeviceType("00000000000000008080000000041410");
        device.setMac("***");
        device.setName("测试空调");
        returnArr.add(device);
        returnJSON.put("Devices", returnArr);
        return returnJSON;
    }

    private void handleDeviceArrayCache(JSONArray jsonArray) {
        if (jsonArray == null || jsonArray.size() < 1)
            return;
        if (DeviceCache.DeviceCacheMap == null)
            DeviceCache.DeviceCacheMap = new HashMap<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject objJSON = jsonArray.getJSONObject(i);
            DeviceCache.DeviceCacheMap.put(objJSON.getString("DeviceID"), objJSON);
        }
    }
}
