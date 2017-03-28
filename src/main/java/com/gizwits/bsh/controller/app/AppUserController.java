package com.gizwits.bsh.controller.app;

import com.gizwits.bsh.annotation.AccessLogging;
import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.resvo.DeviceBindResVO;
import com.gizwits.bsh.bean.resvo.DeviceUnbindResVO;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.service.UserOperatorService;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;

/**
 * 博西操作其他云平台--用户相关
 * Created by zhl on 2016/11/21.
 */
@RestController
@RequestMapping(value = "/bsh/user")
@ApiIgnore
public class AppUserController {

    @Autowired
    private UserOperatorService userOperatorService;

    /**
     * 用户登录获取Token
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String userLogin(){
        //// TODO: 2016/11/24 只有海信的才做需要token,海尔的暂时还不需要登录操作
        userOperatorService.loginThirdPartyCloud();
        return "login";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public void userLogout(){}

    /**
     * 用户绑定设备
     */
    //@AccessLogging(description = "用户绑定设备")
    @RequestMapping(value = "/bindDevices",method = RequestMethod.POST)
    @ResponseBody
    public ResultMap bindUserDevice(@Valid @RequestBody DeviceBindResVO vo, BindingResult result){
        if(result.hasFieldErrors("UserID")){
            return new ResultMap(ErrType.ILLEGAL_PARAM,"UserID cannot be empty");
        }else if(result.hasFieldErrors("PlatID")){
            return new ResultMap(ErrType.ILLEGAL_PARAM,"PlatID cannot be empty");
        }else if(result.hasFieldErrors("DeviceID")){
            return new ResultMap(ErrType.ILLEGAL_PARAM,"DeviceID cannot be empty");
        }
        return userOperatorService.bindUserDevice(vo);
    }

    //@AccessLogging(description = "用户解除设备绑定")
    @RequestMapping(value = "/unbindDevices",method = RequestMethod.DELETE)
    @ResponseBody
    public ResultMap unbindUserDevice(@Valid @RequestBody DeviceUnbindResVO vo, BindingResult result){
        if(result.hasFieldErrors("UserID")){
            return new ResultMap(ErrType.ILLEGAL_PARAM,"UserID cannot be empty");
        }else if(result.hasFieldErrors("DeviceID")){
            return new ResultMap(ErrType.ILLEGAL_PARAM,"DeviceID cannot be empty");
        }
        return userOperatorService.unbindUserDevice(vo);
    }

    @RequestMapping(value = "/devices/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultMap findAllDevices(@PathVariable("userId") String userId){
        return userOperatorService.loadUserDevices(userId);
    }

    @RequestMapping(value = "/devices/list",method = RequestMethod.GET)
    @ResponseBody
    public ResultMap findAllDevicess(@RequestParam("userId") String userId){
        return userOperatorService.loadUserDevices(userId);
    }


    /**
     * 获取用户设备列表
     * @return
     */
    @RequestMapping(value = "/devices",method = RequestMethod.GET)
    @ResponseBody
    public ResultMap findAllMyDevices(@RequestParam("userId") String userId){
        return userOperatorService.loadUserDevices(userId);
        //return userOperatorService.loadUserAllDevices();
    }
}
