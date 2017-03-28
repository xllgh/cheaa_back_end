package com.gizwits.bsh.controller.web;

import com.gizwits.bsh.annotation.AccessLogging;
import com.gizwits.bsh.bean.reqvo.web.AdministratorListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.model.entity.SystemUser;
import com.gizwits.bsh.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员管理
 */
@Controller
@RequestMapping("/web/administrator")
public class WebAdministratorController {

    @Autowired
    AdministratorService administratorService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String gotoAdministratorList(HttpServletRequest request) {
        request.setAttribute("role", administratorService.getAdministratorRole());
        return "administrator/administrator_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReviewVO<SystemUser> getAdministratorList(AdministratorListReqVO reqVO) {
        return administratorService.getAdministratorList(reqVO);
    }

    @RequestMapping(value = "/checkUsername",method = RequestMethod.GET)
    @ResponseBody
    public boolean checkUsername(@RequestParam("userName")String userName){
        return administratorService.checkUsername(userName);
    }

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String gotoAddAdministrator(){
        return "administrator/administrator_new";
    }

    @AccessLogging(description = "添加系统管理员")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public BaseRespVO addAdministrator(@RequestBody SystemUser systemUser){
        return administratorService.saveNewAdministrator(systemUser);
    }

    @AccessLogging(description = "删除系统管理员")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseRespVO deleteAdministrator(@RequestParam(value = "id") String id) {
        return administratorService.deleteAdministrator(id);
    }

}
