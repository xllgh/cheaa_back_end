package com.gizwits.bsh.service.impl;

import com.alibaba.fastjson.JSON;
import com.gizwits.bsh.annotation.AccessLogging;
import com.gizwits.bsh.mapper.AccessLogMapper;
import com.gizwits.bsh.model.entity.AccessLog;
import com.gizwits.bsh.util.GeneratorKit;
import com.gizwits.bsh.util.IpKit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * Created by Vincent on 2015/11/9.
 */
@Aspect
@Component
public class AccessLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogAspect.class);

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Pointcut("@annotation(com.gizwits.bsh.annotation.AccessLogging)")
    public void controllerAspect(){
    }

    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        AccessLog accessLog = new AccessLog();
        Subject subject = SecurityUtils.getSubject();
        Session shiroSession = subject.getSession();
        final Object useridObj = shiroSession.getAttribute("userid");
        if(null!=useridObj){
            String desp = getControllerMethodDescription(joinPoint);
            accessLog.setId(GeneratorKit.generatorID());
            accessLog.setUserId(useridObj.toString());
            accessLog.setAction(desp);
            accessLog.setAccessTime(new Date());
            accessLog.setAccessIp(IpKit.getRealIp(request));
            accessLog.setBodyData(JSON.toJSONString(request.getParameterMap()));
            this.handleLog(request,desp,accessLog);
           // userAccessLogMapper.insertSelective(accessLog);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public  static String getControllerMethodDescription(JoinPoint joinPoint){
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = null;
        try {
            targetClass = Class.forName(targetName);
        } catch (ClassNotFoundException e) {
            logger.warn("====>获取方法名异常",e);
            return "";
        }
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    Annotation anno = method.getAnnotation(AccessLogging.class);
                    if (anno != null) {
                        description = ((AccessLogging)anno).description();
                        break;
                    }
                }
            }
        }
        return description;
    }

    protected void handleLog(HttpServletRequest request, String desp, AccessLog accessLog){
        switch (desp){
            default:
                break;
        }
        accessLogMapper.insertSelective(accessLog);
    }
}
