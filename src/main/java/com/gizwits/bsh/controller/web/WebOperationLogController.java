package com.gizwits.bsh.controller.web;

import com.gizwits.bsh.bean.reqvo.web.OperationLogListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 操作日志
 */
@Controller
@RequestMapping("/web/log")
public class WebOperationLogController {

    @Autowired
    OperationLogService operationLogService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String gotoOperationLogList() {
        return "log/operation_log_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReviewVO<Map> getOperationLogList(OperationLogListReqVO reqVO) {
        return operationLogService.getLogList(reqVO);
    }
}
