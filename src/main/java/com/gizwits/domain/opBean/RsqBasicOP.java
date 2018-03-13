package com.gizwits.domain.opBean;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by neil on 2016/11/6.
 */
public class RsqBasicOP {

    @NotNull
    @JsonProperty("DeviceID")
    private String DeviceID;

    @NotNull
    @JsonProperty("DeviceAttr")
    private JSONObject DeviceAttr;

 /*   @NotNull
    @JsonProperty("UserID")
    private String UserID;

    public void setUserID(String userID){

	UserID = userID;
    }
    
    public String getUserID(){return UserID;}*/
     
    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getDeviceID(){return DeviceID;}

    public JSONObject getDeviceAttr() {
        return DeviceAttr;
    }


    public void setDeviceAttr(JSONObject deviceAttr) {
        DeviceAttr = deviceAttr;
    }
}
