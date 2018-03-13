package com.gizwits.bsh.controller.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gizwits.bsh.bean.RetObject;
import com.gizwits.bsh.bean.reqvo.BSHDeviceReqVO;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.bean.resvo.DeviceStatusResVO;
import com.gizwits.bsh.bean.resvo.DeviceResVO;
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
import org.cheaa.interconnection.spi.IUDeviceIdConvert;
import com.gizwits.bsh.bean.reqvo.LoginReqVO;
import com.gizwits.bsh.bean.resvo.LoginResVO;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONObject;
import java.util.Date;
import java.util.Map;

/**
 * 博西设备接口
 */
@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "博西设备接口")
public class TranslatorController {

    private static final Logger logger = LoggerFactory.getLogger(TranslatorController.class);

    @Autowired
    IUDeviceIdConvert udeviceIdConvert;

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
        //    return new Error("请求头accessToken不合法");
        }
        return null;
    }

    private Error validateRequestBodyUserID(BSHDeviceReqVO body) {
        if (body == null || body.getUserID() == null) {
            return new Error("请求体UserID不合法");
        }
        return null;
    }
    private Error validateLoginBodyError(LoginReqVO body){
	if (body == null || body.getLoginID() == null){
	    return new Error("Login Request don't have valid loginID");
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
 * Login API 
 * Will always return 200 if a valid account in payload
 */
    
    @ApiOperation(value="Login", notes="Login action")
    @RequestMapping(value="/user/login",method= RequestMethod.POST)
    @ResponseBody
    public LoginResVO loginHomeconnect(@ApiParam(value = "account login", required = true)@RequestHeader(value = "PlatID", required = true) String platID,@RequestHeader(value = "AppID", required = false) String appID,
					@RequestHeader HttpHeaders httpHeaders,
					@RequestBody(required = false) LoginReqVO body,HttpServletResponse response){
	String url = "/api/translator/user/login";
	LoginResVO result = new LoginResVO(RetObject.fail());
	Map<String,String> headerMap = new HashedMap();
	headerMap.put("PlatID", platID);
	headerMap.put("AppID", appID);
	String headers = getJSONString(headerMap);
	String bodyText = getJSONString(body);
	Error headerError = validateHeaders(platID,appID);
	Error bodyError = validateLoginBodyError(body);
	if ( bodyError == null && headerError == null){
		response.addHeader("AccessToken","12345678");
		result = new LoginResVO();
	}
	return result;
}


    /**
 * Logout API
 * 
 * Will always return 200 if a valid account in payload
 */

    @ApiOperation(value="Login", notes="Login action")
    @RequestMapping(value="/user/logout",method= RequestMethod.POST)
    @ResponseBody
    public RetObject logoutHomeconnect(@ApiParam(value = "account logout", required = true)@RequestHeader(value = "PlatID", required = true) String platID,@RequestHeader(value = "AppID", required = false) String appID,
                                        @RequestHeader HttpHeaders httpHeaders,
					@RequestHeader(value = "AccessToken", required = true) String AccessToken,
                                        @RequestBody(required = false) LoginReqVO body){
        String url = "/api/translator/user/logout";
        RetObject result = RetObject.fail();
        Map<String,String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);
        Error headerError = validateHeaders(platID,appID);
        Error bodyError = validateLoginBodyError(body);
        if (bodyError == null && headerError == null){
                result = RetObject.success();
        }
        return result;
}

    /**
 * unregister API
 * Will always return 200 if a valid account in payload
 */

    @ApiOperation(value="Login", notes="Login action")
    @RequestMapping(value="/user/unregister",method= RequestMethod.POST)
    @ResponseBody
    public RetObject unregisterHomeconnect(@ApiParam(value = "account unregister", required = true)@RequestHeader(value = "PlatID", required = true) String platID,@RequestHeader(value = "AppID", required = false) String appID,
                                        @RequestHeader HttpHeaders httpHeaders,
                                        @RequestBody(required = false) BSHDeviceReqVO body){
        String url = "/v1/user/unregister";
        RetObject result = RetObject.tokenError();
        Map<String,String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);
        Error headerError = validateHeaders(platID,appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (bodyError == null && headerError == null){
                result = RetObject.success();
        }
        return result;
}



	/**
 * Bind device 
 * Will always return 200 if a valid account and HAID is given.
 */
    @ApiOperation(value="Bind", notes="Bind action")
    @RequestMapping(value="/user/bindDevices",method = RequestMethod.POST)
    @ResponseBody
    public RetObject bindDevice(@ApiParam(value="bind device", required = true)@RequestHeader(value = "AppID", required = false) String appID,
					@RequestHeader(value = "PlatID", required = true) String platID,
					@RequestHeader HttpHeaders httpHeaders,
					@ApiParam(name="Object",value = "RequestObject",required = true)
					@RequestBody(required = false) BSHDeviceReqVO body){
	String url = "/api/translator/user/bindDevices";
	RetObject result = RetObject.deviceNotExist();
	Map<String,String> headerMap = new HashedMap();
	headerMap.put("AppID", appID);
	headerMap.put("PlatID", platID);
	String headers = getJSONString(headerMap);
	String bodyText = getJSONString(body);
	Error headerError = validateHeaders(platID, appID);
	Error bodyError = validateRequestBodyUserID(body);
	if (bodyError == null && headerError == null && body.getDeviceID() != null){
	try{
		String s = udeviceIdConvert.decode(body.getDeviceID());
		if (s != null){
			result = RetObject.success();
		}else{
			result = RetObject.fail();
}
}catch(Error error){result = RetObject.fail();}catch(Exception exception){exception.printStackTrace();}

}
	return result;
}

        /**
 * Unbind device
 * Will always return 200 if a valid account and HAID is given.
 */
    @ApiOperation(value="Unbind", notes="Unbind action")
    @RequestMapping(value="/user/unbindDevices",method = RequestMethod.POST)
    @ResponseBody
    public RetObject unbindDevice(@ApiParam(value="unbind device", required = true)@RequestHeader(value = "PlatID", required = true) String platID, @RequestHeader(value = "AppID", required = false) String appID,
                                        @RequestHeader HttpHeaders httpHeaders,
                                        @ApiParam(name="Object",value = "RequestObject",required = true)
                                        @RequestBody(required = false) BSHDeviceReqVO body){
        String url = "/api/translator/user/unbindDevices";
        RetObject result = RetObject.deviceNotExist();
        Map<String,String> headerMap = new HashedMap();
        headerMap.put("AppID", appID);
	headerMap.put("PlatID", platID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);
	Error headerError = validateHeaders(platID,appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (bodyError == null && headerError == null && body.getDeviceID() != null){
        try{
                String s = udeviceIdConvert.decode(body.getDeviceID());
                if (s != null){
                        result = RetObject.success();
                }else{
                        result = RetObject.fail();
}
}catch (Error error){result = RetObject.fail();}catch (Exception exception){exception.printStackTrace();}
}
        return result;
}

  
    /**
     * 查询设备列表
     * @param platID
     * @return
     */
    @ApiOperation(value="查询设备列表", notes="查询设备列表")
    @RequestMapping(value = "/users/devices", method = RequestMethod.POST)
    @ResponseBody
    public DeviceListResVO queryDeviceList(@ApiParam(value = "平台ID，博西：bsh", required = true) 
					   @RequestHeader(value = "PlatID", required = true) String platID,
                                           @RequestHeader(value = "AppID", required = false) String appID,
                                           @RequestHeader HttpHeaders httpHeaders,
                                           @RequestBody(required = false) BSHDeviceReqVO body) {
        Date startTime = new Date();
        String url = "/api/translator/users/devices";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
	logger.info (platID + appID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);

        DeviceListResVO result = new DeviceListResVO(RetObject.fail());
        Error headerError = validateHeaders(platID, appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (bodyError == null && headerError == null) {
            try {
		logger.info("parsing");
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
    @ApiOperation(value="DeviceInfo", notes="Specific HA info")
    @RequestMapping(value = "/devices/deviceInfo", method = RequestMethod.POST)
    @ResponseBody
    public DeviceResVO queryDeviceInfo(@ApiParam(value = "PlatID", required =true)
					   @RequestHeader(value = "PlatID", required = true) String platID,
					   @RequestHeader(value = "AppID", required = false) String appID,
					   @RequestHeader HttpHeaders httpHeaders,
					   @RequestBody(required = false) BSHDeviceReqVO body){
	Date startTime = new Date();
	String url = "v1/devices/deviceInfo";
	Map<String, String> headerMap = new HashedMap();
	headerMap.put("PlatID", platID);
	headerMap.put("AppID", appID);
	String headers = getJSONString(headerMap);
	String bodyText = getJSONString(body);
	DeviceResVO result = new DeviceResVO(RetObject.fail());
	Error headerError = validateHeaders(platID,appID);
	Error bodyError = validateRequestBodyUserID(body);
	if(bodyError == null && headerError ==null){
		try{
			result = homeConnectService.getDeviceInfo(body.getUserID(),body.getDeviceID());
		}catch (Error error){
			result = new DeviceResVO(new RetObject("500", error.getMessage()));
		}catch (Exception exception){
			exception.printStackTrace();
		}
	}
	String response = getJSONString(result);
	Date endTime = new Date();
	log(url,headers,bodyText,response,startTime,endTime);
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
    public DeviceStatusResVO queryDeviceStatus(@ApiParam(value = "平台ID，博西：bsh", required = true) @RequestHeader(value = "PlatID", required = true) String platID,
                                                      @RequestHeader(value = "AppID", required = false) String appID,
                                                      @ApiParam(name = "Object", value = "请求对象", required = true) @RequestBody(required = false) BSHDeviceReqVO body) {
        Date startTime = new Date();
        String url = "/api/translator/devices/deviceStatus";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);

        DeviceStatusResVO result = new DeviceStatusResVO(RetObject.fail());
        Error headerError = validateHeaders(platID, appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (bodyError == null && body.getDeviceID() != null) {
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

    @ApiOperation(value="FalseGateway",notes="test")
    @RequestMapping(value="/dataservices/dataGateway", method = RequestMethod.POST)
    @ResponseBody
    public RetObject dataGateway(@ApiParam(value="PlatiD", required = false) @RequestHeader(value = "PlatID", required = false)String platID,
				@RequestHeader(value = "AppID", required = false) String appID,
				@ApiParam(name = "Object", value = "Request Object", required = false) @RequestBody(required = false)JSONObject body){
		logger.info("Header" + platID + appID);
		RetObject result = new RetObject();
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
    public RetObject operateDevice(@ApiParam(value = "平台ID，博西：bsh", required = true) @RequestHeader(value = "PlatID", required = true) String platID,
                                   @RequestHeader(value = "AppID", required = false) String appID,
                                   @RequestHeader(value = "AccessToken", required = false) String accessToken,
                                   @ApiParam(name = "Object", value = "请求对象", required = true) @RequestBody(required = false) BSHDeviceReqVO body) {
        Date startTime = new Date();
        String url = "/api/translator/devices/op";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        headerMap.put("AccessToken", accessToken);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);

        RetObject result = RetObject.init();
	RetObject dataGateway = RetObject.fail();
	RetObject dateGatewayCoffee = RetObject.fail();
        Error headerError = validateHeaders(platID, appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (bodyError == null && headerError == null
                && body.getDeviceID() != null && body.getDeviceAttr() != null) {
            try {
		//system.out.println("Got Attr\n");
                result = homeConnectService.operateDevice(body.getUserID(), body.getDeviceID(), body.getDeviceAttr());
		if("200".equals(result.getRetCode())){
			if("000011".equals(platID)){
					dataGateway = homeConnectService.dataGatewayCoffee(body.getUserID(),body.getDeviceID(),platID);
				}else{
			dataGateway = homeConnectService.dataGateway(body.getUserID(),body.getDeviceID(),platID);}
			//logger.info("May be I got the gateway result?");
			//dataGateway = homeConnectService.dataGatewayCoffee(body.getUserID(),body.getDeviceID(),platID);
			//logger.info("Maybe I tell them coffee machine is not runnig?");}
			}
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
