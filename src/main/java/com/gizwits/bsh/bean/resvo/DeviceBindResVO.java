package com.gizwits.bsh.bean.resvo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by zhl on 2016/11/30.
 */
public class DeviceBindResVO {
    @JsonProperty(value = "UserID")
    @NotNull
    private String UserID;
    @JsonProperty(value = "DeviceID")
    @NotNull
    private String DeviceID;
    @JsonProperty(value = "PlatID")
    @NotNull
    private String PlatID;
    @JsonProperty(value = "DeviceType")
    @NotNull
    private String DeviceType;
    @JsonProperty(value = "UserName")
    private String UserName;


    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public DeviceBindResVO() {
    }

    public DeviceBindResVO(String userID, String deviceID, String platID) {
        UserID = userID;
        DeviceID = deviceID;
        PlatID = platID;
    }

    public DeviceBindResVO(String userID, String deviceID) {
        UserID = userID;
        DeviceID = deviceID;
    }

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

    public String getPlatID() {
        return PlatID;
    }

    public void setPlatID(String platID) {
        PlatID = platID;
    }
}
