package com.gizwits.bsh.Interceptor;

import com.gizwits.bsh.bean.RetObject;
import com.gizwits.bsh.util.WriterKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhl on 2016/11/21.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter{
    private final static Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler){
//        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
//            String platID =  request.getHeader("PlatID");
//            String appId = request.getHeader("AppID");
//            logger.info("get in interceptor ,PlatID:{}",platID);
//            logger.info("get in interceptor ,AppID:{}",appId);
//            if(StringUtils.isEmpty(platID)){
//                WriterKit.renderJson(response,RetObject.fail.put("msg","PlatID不能为空").toJSONString());
//                return false;
//            }
//            if(StringUtils.isEmpty(appId)){
//                WriterKit.renderJson(response,RetObject.fail.put("msg","AppID不能为空").toJSONString());
//                return false;
//            }
//            return true;
//        }else{
//            return true;
//        }
        return true;
    }
}
