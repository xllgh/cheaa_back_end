package com.gizwits.bsh.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by junji on 2015/3/18.
 */
public class IpKit {
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getRealIpV2(HttpServletRequest request) {
        String accessIP = request.getHeader("x-forwarded-for");
        if (null == accessIP)
            return request.getRemoteAddr();
        return accessIP;
    }
}