package com.gizwits.bsh.controller.web;

import com.gizwits.bsh.annotation.AccessLogging;
import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.bsh.bean.reqvo.web.UserDeviceListReqVO;
import com.gizwits.bsh.bean.reqvo.web.UserListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.bean.resvo.web.UserDeviceVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.model.entity.PlatUser;
import com.gizwits.bsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhl on 2016/11/21.
 */
@Controller
@RequestMapping("/web/users")
public class WebUserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String gotoUserList() {
        return "user/user_list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ReviewVO<PlatUser> getUserList(UserListReqVO reqVO) {
        return userService.getUserList(reqVO);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public ResultMap<PlatUser> getUserInfo(@RequestParam(value = "userId") String userId) {
        PlatUser user = userService.getUser(userId);
        if (user == null) {
            return new ResultMap<>(ErrType.FAIL);
        }
        return new ResultMap<>(ErrType.SUCCESS, user);
    }

    @RequestMapping(value = "/devices/list", method = RequestMethod.GET)
    public String gotoUserDevicesList() {
        return "user/user_device_list";
    }

    @RequestMapping(value = "/devices/list", method = RequestMethod.POST)
    @ResponseBody
    public ReviewVO<UserDeviceVO> gotoUserDevicesList(UserDeviceListReqVO reqVO) {
        return userService.getUserDeviceList(reqVO);
    }

    @AccessLogging(description = "管理员解除用户设备绑定")
    @RequestMapping(value = "/devices/unbind", method = RequestMethod.POST)
    @ResponseBody
    public BaseRespVO unbindUserDevice(@RequestParam(value = "bindingId") String bindingId) {
        return userService.unbindDevice(bindingId);
    }

    @AccessLogging(description = "管理员修改用户查看全列表权限")
    @RequestMapping(value = "/changePermission", method = RequestMethod.POST)
    @ResponseBody
    public BaseRespVO changeUserViewDevicePermission(@RequestBody PlatUser platUser){
        return userService.changeUserPermission(platUser);
    }
}
