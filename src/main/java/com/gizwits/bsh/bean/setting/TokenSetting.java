package com.gizwits.bsh.bean.setting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.HttpRspObject;
import com.gizwits.bsh.enums.MultiCloudHost;
import com.gizwits.bsh.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class TokenSetting {
    private static Logger logger = LoggerFactory.getLogger(TokenSetting.class);

    private static long REFRESHTIME_SECONDS = 2*60*60; //刷新AccessToken
    private static String AccessTokenStr = "AccessToken";
    private static String UserIDStr = "UserID";



    private static ScheduledExecutorService timer = Executors
            .newSingleThreadScheduledExecutor(new ThreadFactory() {
                @Override
                public Thread newThread(Runnable run) {
                    Thread t = new Thread(run);
                    t.setDaemon(true);
                    return t;
                }

            });

    static {
        initTimer();
    }

    /**
     * 获取凭证accessToken
     */
    public static String getTokenStr(MultiCloudHost cloudHost) {
        if (cloudHost == null)
            return null;
        if (tokenMap == null)
            platLogin();

        return tokenMap.get(cloudHost.getHost()).getString(AccessTokenStr);
    }

    /**
     * 获取当前云的UserID
     */
    public static String getUserID(MultiCloudHost cloudHost) {
        if (cloudHost == null)
            return null;
        if (tokenMap == null)
            platLogin();

        return tokenMap.get(cloudHost.getHost()).getString(UserIDStr);
    }


    /**
     *  刷新凭证并更新全局凭证值(token 与 JSTicket)
     */
    private static void refreshToken() {
        try {
            System.out.println("refresh token.....");
            platLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 定时刷新token
     */
    private static void initTimer() {

        // 延迟时间100秒内随机
        long delay = (long) (20 * (new Random().nextDouble()));
        timer.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                    refreshToken();
            }
        }, delay, REFRESHTIME_SECONDS, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                timer.shutdown();
            }
        }));
    }

    /**
     * 凭证的存储需要全局唯一
     * <p>
     * 单机部署情况下可以存在内存中 <br>
     * 分布式情况需要存在集中缓存或DB中
     */
    private static Map<String, JSONObject> tokenMap = null;


    /**
     * 第三方登录
     * @return
     */
    public static void platLogin() {

        for (MultiCloudHost cloudHost : MultiCloudHost.values()) {
            //todo
            if (cloudHost == MultiCloudHost.HAIER)
                continue;
            String host = cloudHost.getHost();
            Map<String, String> headers = new HashMap<>();

            headers.put("PlatID", EnvSetting.PlatID);
            headers.put("AppID", EnvSetting.AppID);
            headers.put("AuthToken", EnvSetting.AuthToken);
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            String param = "AccType=" + EnvSetting.AccType + "&UUserID=" + EnvSetting.UUserID;

            if (tokenMap == null) {
                tokenMap = new HashMap<>();
            }
            try {
                HttpRspObject httpRspObject = HttpUtil.post("http://"+host+"/1.0/user/login", headers, param, "application/x-www-form-urlencoded");
                if (JSON.parseObject(httpRspObject.getBody()).getIntValue("RetCode") == 200) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(AccessTokenStr, httpRspObject.getHeadersMap().get(AccessTokenStr));
                    jsonObject.put(UserIDStr, JSON.parseObject(httpRspObject.getBody()).getString(UserIDStr));
                    tokenMap.put(host, jsonObject);

                    logger.info("第三方登录验证OK host:{} , {}", host, jsonObject.getString(UserIDStr));

                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("第三方登录验证出错host:{}", host);
            }

        }

    }


}
