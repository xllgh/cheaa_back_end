package com.gizwits.bsh.controller.web;

import com.gizwits.bsh.annotation.AccessLogging;
import com.gizwits.bsh.bean.reqvo.web.DeviceListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zhl on 2016/11/21.
 */
@Controller
@RequestMapping("/web/devices")
public class WebDevicesController {

    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String gotoDeviceList() {
        return "device/device_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReviewVO<Map> getDeviceList(DeviceListReqVO deviceListReqVO) {
        return deviceService.getDeviceList(deviceListReqVO);
    }

    @AccessLogging(description = "删除设备")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseRespVO getDeviceList(@RequestParam(value = "deviceId") String deviceId) {
        return deviceService.deleteDevice(deviceId);
    }

}
