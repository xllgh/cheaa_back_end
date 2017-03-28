package com.gizwits.bsh.controller.app;

import com.gizwits.bsh.bean.setting.EnvSetting;
import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.service.QueryService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by neil on 2016/11/5.
 */
@Controller
@RequestMapping("/query")
@ApiIgnore
public class QueryController {

    @Autowired
    private QueryService queryService;

    @RequestMapping(value = "/view/devices", method = RequestMethod.GET)
    public String devicesView() {
        return "devices/devices";
    }

    /**
     * 用户设备列表
     * @param type
     * @return
     */
    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap devices(@RequestParam(value = "username", required = false) String username,
                             @RequestParam(value = "type", required = false) String type) {

        if (StringUtils.isEmpty(username))
            username = EnvSetting.UUserID;
        return queryService.queryDevices(username, type);
    }

    @RequestMapping(value = "/deviceStatus", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap deviceStatusView(String DeviceID) {

        return queryService.queryDeviceStatus(DeviceID);
    }



    @RequestMapping(value = "/view/deviceStatus", method = RequestMethod.GET)
    public String deviceStatusView() {
        return "/devices/deviceStatus";
    }
}
