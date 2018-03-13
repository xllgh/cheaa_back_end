package com.gizwits;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.bean.setting.EnvSetting;
import com.gizwits.bsh.bean.setting.TokenSetting;
import org.junit.Test;
import org.cheaa.interconnection.server.sdk.ServerSDK;
import org.cheaa.interconnection.client.sdk.ClientSDK;
import org.apache.log4j.Logger;
import java.net.URLEncoder;

/**
 * Created by neil on 2016/11/5.
 */
public class TestAccessToken {

   // @Test
    //public void testRefreshToken() {
    //    TokenSetting.platLogin();
   // }
    private static final Logger logger = Logger.getLogger(TestAccessToken.class);
    @Test
    public void testJsonToWWWEncode() {
     //   JSONObject repuestBody = new JSONObject();
      //  repuestBody.put("AccType", EnvSetting.AccType);
     //   repuestBody.put("UUserID", EnvSetting.UUserID);
      //  System.out.println(URLEncoder.encode(repuestBody.toString()));
    }
//    @Test
    /*public void SDKinittest(){
	                try {
                        ServerSDK.instance().init("/home/ec2-user/CHEAA/CHEAA_latest/cheaa_back_end/src/main/resources/configure");
                        ClientSDK.instance().init("/home/ec2-user/CHEAA/CHEAA_latest/cheaa_back_end/src/maib/resources/configure");
			System.out.println("sdk init successfully");
                } catch (Exception e){
			System.out.println("failed" + e );
                        logger.info("SDK initialize failed.");
                        System.exit(-1);
}*/
}

