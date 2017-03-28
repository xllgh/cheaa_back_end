package com.gizwits;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.setting.EnvSetting;
import com.gizwits.bsh.bean.setting.TokenSetting;
import org.junit.Test;

import java.net.URLEncoder;

/**
 * Created by neil on 2016/11/5.
 */
public class TestAccessToken {

    @Test
    public void testRefreshToken() {
        TokenSetting.platLogin();
    }

    @Test
    public void testJsonToWWWEncode() {
        JSONObject repuestBody = new JSONObject();
        repuestBody.put("AccType", EnvSetting.AccType);
        repuestBody.put("UUserID", EnvSetting.UUserID);
        System.out.println(URLEncoder.encode(repuestBody.toString()));
    }
}
