package com.gizwits.bsh.controller.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gizwits.bsh.bean.RetObject;
import com.gizwits.bsh.bean.reqvo.BSHDeviceReqVO;
import com.gizwits.bsh.bean.resvo.DeviceListResVO;
import com.gizwits.bsh.bean.resvo.DeviceStatusResVO;
import com.gizwits.bsh.mapper.TranslatorLogMapper;
import com.gizwits.bsh.model.entity.TranslatorLog;
import com.gizwits.bsh.service.MykieService;
import com.gizwits.domain.opBean.BasicOP;
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
import com.gizwits.domain.opBean.RsqBasicOP;
import java.util.Date;
import java.util.Map;




@Controller
@RequestMapping("/mykie")
@Api(value= "/mykie" , description= "Mykie Interface" )
public class MykieController {

	private static final Logger logger = LoggerFactory.getLogger(MykieController.class);

	@Autowired
	TranslatorLogMapper translatorLogMapper;

	@Autowired
	MykieService mykieService;

	private Error validateHeaders(String platID, String appID){
		return null;
	}

	/*private Error validateRequestBodyUserID(RsqBasicOP body){
		if (body == null || body.getUserID() == null){
			return new Error("Invalid Request Body");	
		}
		return null;}*/

	private Error validateRequestBodyUserID(BSHDeviceReqVO body){
                if (body == null || body.getUserID() == null){
                        return new Error("Invalid Request Body");
                }
                return null;}

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

    @ApiOperation(value="DeviceList", notes="List all device in specific account")
    @RequestMapping(value = "/users/devices", method = RequestMethod.POST)
    @ResponseBody
    public DeviceListResVO queryDeviceList(@ApiParam(value = "Device List", required = true) @RequestHeader(value = "PlatID", required = true) String platID,
							@RequestHeader(value = "AppID" , required = false) String appID,
							@ApiParam(name="Object", value = "RequestOb", required = true) @RequestBody(required = true) BasicOP body){
	Date startTime = new Date();
	String url = "/mykie/users/devices";
	Map<String, String> headerMap = new HashedMap();
	headerMap.put("PlatID",platID);
	headerMap.put("AppID",appID);
	String headers = getJSONString(headerMap);
	String bodyText = getJSONString(body);
	DeviceListResVO result = new DeviceListResVO(RetObject.fail());
	Error headerError = validateHeaders(platID,appID);
	//Error bodyError = validateRequestBodyUserID(body);
	
	if (headerError == null && body.getUser()!= null ){
		try{
			result = mykieService.getDevices(body.getUser(),"");//,body.getTargetPlat());
		}
		catch(Error error){
			result = new DeviceListResVO(new RetObject("500",error.getMessage()));
			}
		catch(Exception exception){
			exception.printStackTrace();
}}
	String response = getJSONString(result);
	Date endTime = new Date();
	log(url,headers,bodyText,response,startTime, endTime);
	return result;
}
	

    @ApiOperation(value="查询设备状态", notes="查询设备当前状态")
    @RequestMapping(value = "/devices/deviceStatus", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject queryDeviceStatus(@ApiParam(value = "Mykie Device Status", required = true) @RequestHeader(value = "PlatID", required = false) String platID,
                                                      @RequestHeader(value = "AppID", required = false) String appID,
                                                      @ApiParam(name = "Object", value = "请求对象", required = true) @RequestBody(required = false) BSHDeviceReqVO body) {
        Date startTime = new Date();
        String url = "/mykie/devices/deviceStatus";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);
	JSONObject result = new JSONObject();
        //DeviceStatusResVO result = new DeviceStatusResVO(RetObject.fail());
        Error headerError = validateHeaders(platID, appID);
        Error bodyError = validateRequestBodyUserID(body);
        if (headerError == null && bodyError == null && body.getDeviceID() != null) {
            try {
		String targetPlat = body.getDeviceID().substring(0,6);
		result = mykieService.getDeviceStatus(body.getUserID(),body.getDeviceID(),targetPlat);
		}
                //result = homeConnectService.getDeviceStatus(body.getUserID(), body.getDeviceID());
             catch (Error error) {
		logger.info(""+error);
	//	result = new DeviceStatusResVO(new RetObject("500", error.getMessage()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }}
        

        String response = getJSONString(result);
        Date endTime = new Date();
        log(url, headers, bodyText, response, startTime, endTime);
        return result;
	
}

    @ApiOperation(value="操作设备", notes="修改设备状态")
    @RequestMapping(value = "/devices/op", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject operateDevice(@ApiParam(value = "mykie device op", required = true) @RequestHeader(value = "PlatID", required = false) String platID,
                                   @RequestHeader(value = "AppID", required = false) String appID,
                                   @RequestHeader(value = "AccessToken", required = false) String accessToken,
                                   @ApiParam(name = "Object", value = "请求对象", required = true) @RequestBody(required = false) RsqBasicOP body) {
        Date startTime = new Date();
        String url = "/mykie/devices/op";
        Map<String, String> headerMap = new HashedMap();
        headerMap.put("PlatID", platID);
        headerMap.put("AppID", appID);
        headerMap.put("AccessToken", accessToken);
        String headers = getJSONString(headerMap);
        String bodyText = getJSONString(body);

        JSONObject result = new JSONObject();
        Error headerError = validateHeaders(platID, appID);
        //Error bodyError = validateRequestBodyUserID(body);
        if (headerError == null /*&& bodyError == null*/
                && body.getDeviceID() != null && body.getDeviceAttr() != null) {
            try {
		String targetPlat = body.getDeviceID().substring(0,6);
                result = mykieService.operateDevice(body,targetPlat);
            } catch (Error error) {
                //result = new RetObject("500", error.getMessage());
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
