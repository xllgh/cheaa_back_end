package com.gizwits.bsh.controller.app;

import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.service.DevOPService;
import com.gizwits.domain.opBean.RsqBasicOP;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 博西操作其他平台设备--设备操作
 * Created by neil on 2016/11/6.
 */
@Controller
@RequestMapping("/devices")
@ApiIgnore
public class DevOPController {

    @Autowired
    private DevOPService devOPService;

    @RequestMapping(value = "view/op", method = RequestMethod.GET)
    public String opView() {
        return "devices/opView";
    }

    @RequestMapping(value = "/op", method = RequestMethod.POST)
    @ResponseBody
    public ResultMap op( RsqBasicOP rsqBasicOP) {

        return devOPService.opDevice(rsqBasicOP);
    }

}
