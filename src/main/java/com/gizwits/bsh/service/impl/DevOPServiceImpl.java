package com.gizwits.bsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.HttpRspObject;
import com.gizwits.bsh.enums.MultiCloudHost;
import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.setting.EnvSetting;
import com.gizwits.bsh.bean.setting.TokenSetting;
import com.gizwits.bsh.service.DevOPService;
import com.gizwits.domain.opBean.RsqBasicOP;
import com.gizwits.bsh.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by neil on 2016/11/6.
 */
@Service
public class DevOPServiceImpl implements DevOPService {

    private static Logger logger = LoggerFactory.getLogger(DevOPServiceImpl.class);

    @Override
    public ResultMap bindDev() {
        return null;
    }

    @Override
    public ResultMap unBindDev() {
        return null;
    }

    @Override
    public ResultMap opDevice(RsqBasicOP rsqBasicOP) {

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
}
