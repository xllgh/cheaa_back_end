package com.gizwits.bsh.controller.app;

import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.service.DevOPService;
import com.gizwits.bsh.service.DeviceOperatorService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.gizwits.bsh.service.QueryService;
import com.gizwits.domain.opBean.RsqBasicOP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * 博西设备操作第三方设备
 * Created by zhl on 2016/11/21.
 */
@Controller
@RequestMapping("/bsh/devices")
@ApiIgnore
public class AppDevicesController {

    @Autowired
    private DeviceOperatorService deviceOperatorService;

    /**
     * 设备状态查询
     * @param deviceID
     * @return
     */
    @RequestMapping(value = "/deviceStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap getDevicesStatus(@RequestParam(value = "DeviceID",required = true) String deviceID){
        return deviceOperatorService.handleDeviceStatusRequest(deviceID);
        //return queryService.queryDeviceStatus(deviceID);
    }

    @RequestMapping(value = "/deviceType/{plat_id}/{type_code}",method = RequestMethod.GET)
    @ResponseBody
    public ResultMap getDeviceType(@PathVariable("plat_id")String platId,@PathVariable("type_code")String typeCode){
        return deviceOperatorService.getDeviceTypeAndPlatId(typeCode,platId);
    }

    /**
     * 设备操作
     */
    @RequestMapping(value = "/op",method = RequestMethod.POST)
    @ResponseBody
    public ResultMap operatorDevices(@Valid @RequestBody RsqBasicOP rsqBasicOP,BindingResult result){
        if(result.hasErrors()){
            return new ResultMap(ErrType.ILLEGAL_PARAM);
        }
        return deviceOperatorService.handleOperatorRequest(rsqBasicOP);
        //return devOPService.opDevice(rsqBasicOP);
    }
}
