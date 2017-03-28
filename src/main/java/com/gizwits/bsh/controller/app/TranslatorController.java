package com.gizwits.bsh.controller.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gizwits.bsh.bean.RetObject;
import com.gizwits.bsh.bean.reqvo.BSHDeviceReqVO;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.bean.resvo.DeviceStatusResVO;
import com.gizwits.bsh.mapper.TranslatorLogMapper;
import com.gizwits.bsh.model.entity.TranslatorLog;
import com.gizwits.bsh.service.HomeConnectService;
import com.gizwits.bsh.util.GeneratorKit;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 博西设备接口
 */
@Controller
@RequestMapping("/translator")
@Api(value = "/translator", description = "博西设备接口")
public class TranslatorController {

    private static final Logger logger = LoggerFactory.getLogger(TranslatorController.class);

    @Autowired
    HomeConnectService homeConnectService;

    @Autowired
    TranslatorLogMapper translatorLogMapper;

    private Error validateHeaders(String platID, String appID) {
        if (!homeConnectService.isPlatIdSupport(platID)) {
            return new Error("请求头platID不合法");
        }
        if (appID == null) {
//            return new Error("请求头appID不合法");
        }
        return null;
    }

    private Error validateHeaders(String platID, String appID, String accessToken) {
        Error error = validateHeaders(platID, appID);
        if (error != null) {
            return error;
        }
        if (accessToken == null) {
//            return new Error("请求头accessToken不合法");
        }
        return null;
    }

    private Error validateRequestBodyUserID(BSHDeviceReqVO body) {
        if (body == null || body.getUserID() == null) {
            return new Error("请求体UserID不合法");
        }
        return null;
    }

    private String getJSONString(Object body) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(body);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void log(String url, String headers, String body, String response, Date startTime, Date endTime) {
//        String logMessage = "TRANSLATOR LOG:";
//        logMessage += "\n" + url;
//        logMessage += "\n" + headers;
//        logMessage += "\nbody: " + body;
//        logMessage += "\n...\nresponse: " + response;
//        logger.info(logMessage);

        TranslatorLog translatorLog = new TranslatorLog();
        translatorLog.setId(GeneratorKit.getUUID());
        translatorLog.setUrl(url);
        translatorLog.setHeaders(headers);
        translatorLog.setBody(body);
        translatorLog.setResponse(response);
        translatorLog.setStartTime(startTime);
        translatorLog.setEndTime(endTime);
        translatorLogMapper.insertSelective(translatorLog);
    }

    /**
     * 查询设备列表
     * @param platID
     * @return
     */
    @ApiOperation(value="查询设备列表", notes="查询设备列表")
    @RequestMapping(value = "/users/devices", method = RequestMethod.POST)
    @ResponseBody
    public DeviceListResVO queryDeviceList(@ApiParam(value = "平台ID，博西：bsh", required = true) @RequestHeader(value = "PlatID", required = false) String platID,
                                           @RequestHeader(value = "AppID", required = false) String appID,
                                           @RequestHeader HttpHeaders httpHeaders,
                                           @RequestBody(required = false) BSHDeviceReqVO body) {
        Date startTime = new Date();
        String url = "/translator/users/devices";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);

        DeviceListResVO result = new DeviceListResVO(RetObject.fail());
        Error headerError = validateHeaders(platID, appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (headerError == null && bodyError == null) {
            try {
                result = homeConnectService.getDevices(body.getUserID(), body.getDeviceType());
            } catch (Error error) {
                result = new DeviceListResVO(new RetObject("500", error.getMessage()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        String response = getJSONString(result);
        Date endTime = new Date();
        log(url, headers, bodyText, response, startTime, endTime);
        return result;
    }

    /**
     * 查询设备状态
     * @param platID 平台ID，区分设备属于哪个平台
     * @param body 传入需要查询的设备ID
     * @return
     */
    @ApiOperation(value="查询设备状态", notes="查询设备当前状态")
    @RequestMapping(value = "/devices/deviceStatus", method = RequestMethod.POST)
    @ResponseBody
    public DeviceStatusResVO queryDeviceStatus(@ApiParam(value = "平台ID，博西：bsh", required = true) @RequestHeader(value = "PlatID", required = false) String platID,
                                                      @RequestHeader(value = "AppID", required = false) String appID,
                                                      @ApiParam(name = "Object", value = "请求对象", required = true) @RequestBody(required = false) BSHDeviceReqVO body) {
        Date startTime = new Date();
        String url = "/translator/devices/deviceStatus";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);

        DeviceStatusResVO result = new DeviceStatusResVO(RetObject.fail());
        Error headerError = validateHeaders(platID, appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (headerError == null && bodyError == null && body.getDeviceID() != null) {
            try {
                result = homeConnectService.getDeviceStatus(body.getUserID(), body.getDeviceID());
            } catch (Error error) {
                result = new DeviceStatusResVO(new RetObject("500", error.getMessage()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        String response = getJSONString(result);
        Date endTime = new Date();
        log(url, headers, bodyText, response, startTime, endTime);
        return result;
    }

    /**
     * 操作设备
     * @param platID 平台ID，区分设备属于哪个平台
     * @param body 传入设备ID和操作内容
     * @return
     */
    @ApiOperation(value="操作设备", notes="修改设备状态")
    @RequestMapping(value = "/devices/op", method = RequestMethod.POST)
    @ResponseBody
    public RetObject operateDevice(@ApiParam(value = "平台ID，博西：bsh", required = true) @RequestHeader(value = "PlatID", required = false) String platID,
                                   @RequestHeader(value = "AppID", required = false) String appID,
                                   @RequestHeader(value = "AccessToken", required = false) String accessToken,
                                   @ApiParam(name = "Object", value = "请求对象", required = true) @RequestBody(required = false) BSHDeviceReqVO body) {
        Date startTime = new Date();
        String url = "/translator/devices/op";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        headerMap.put("AccessToken", accessToken);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);

        RetObject result = RetObject.fail();
        Error headerError = validateHeaders(platID, appID, accessToken);
        Error bodyError = validateRequestBodyUserID(body);
        if (headerError == null && bodyError == null
                && body.getDeviceID() != null && body.getDeviceAttr() != null) {
            try {
                result = homeConnectService.operateDevice(body.getUserID(), body.getDeviceID(), body.getDeviceAttr());
            } catch (Error error) {
                result = new RetObject("500", error.getMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        String response = getJSONString(result);
        Date endTime = new Date();
        log(url, headers, bodyText, response, startTime, endTime);
        return result;
    }

}
