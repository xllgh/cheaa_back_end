package com.gizwits.bsh.bean.resvo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by zhl on 2016/11/30.
 */
public class DeviceUnbindResVO {
    @JsonProperty(value = "UserID")
    @NotNull
    private String UserID;
    @JsonProperty(value = "DeviceID")
    @NotNull
    private String DeviceID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }
}
