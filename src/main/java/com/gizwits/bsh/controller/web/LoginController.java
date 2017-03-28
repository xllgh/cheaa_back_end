package com.gizwits.bsh.controller.web;

import com.alibaba.fastjson.JSON;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.mapper.SystemUserMapper;
import com.gizwits.bsh.ream.UserPwdTypeToken;
import com.gizwits.bsh.util.WriterKit;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhl on 2016/12/23.
 */
@Controller
public class LoginController {

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SystemUserMapper systemUserMapper;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){
        Subject userLogin = SecurityUtils.getSubject();
        if(userLogin.isAuthenticated()){
            if(logger.isDebugEnabled()){
                logger.debug("===> 已经登录过了");
            }
            return "redirect:indexSkip";
        }if((request.getHeader("x-requested-with")!=null&&request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
                ||(null!=request.getHeader("requestType")&&request.getHeader("requestType").equalsIgnoreCase("ajax"))){
            BaseRespVO baseVO = new BaseRespVO(ErrType.WEB_SESSION_TIMEOUT);
            WriterKit.renderJson(response, JSON.toJSONString(baseVO));
            return null;
        }else {
            return "login";
        }
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public BaseRespVO login(@RequestParam(value = "username") String username, @RequestParam("password") String password){
        Subject userLogin = SecurityUtils.getSubject();
        UserPwdTypeToken token = new UserPwdTypeToken(username,password,1);
        try{
            userLogin.login(token);
            //// TODO: 2016/12/23 login success action

            return new BaseRespVO(ErrType.SUCCESS);
        }catch (UnknownAccountException e){
            token.clear();
            return new BaseRespVO(ErrType.WEB_ACCOUNT_NOTEXISTED);
        }catch (LockedAccountException e){
            token.clear();
            return new BaseRespVO(ErrType.WEB_ACCOUNT_LOCKD);
        }catch (ExcessiveAttemptsException e){
            token.clear();
            return new BaseRespVO(ErrType.WEB_ACCOUNT_LOGIN_LIMIT);
        }catch (AuthenticationException e){
            token.clear();
            if(StringUtils.contains(e.getMessage(),ErrType.WEB_ILLEGAL_ACCOUNT_PASSWORD.getErrmsg())){
                return new BaseRespVO(ErrType.WEB_ILLEGAL_ACCOUNT_PASSWORD);
            }else {
                logger.warn("==>登录异常",e);
                return new BaseRespVO(ErrType.SYSTEM_ERROR);
            }
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject logout = SecurityUtils.getSubject();
        logout.logout();
        return "redirect:login";
    }
}
