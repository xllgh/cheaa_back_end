package com.gizwits.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by neil on 2016/11/6.
 */
public class Device {

    private Object DeviceAttrs;
    private String mac;
    private String DeviceType;
    private String DeviceID;
    private String name;

    public Object getDeviceAttrs() {
        return DeviceAttrs;
    }

    @JsonProperty("DeviceAttrs")
    public void setDeviceAttrs(Object deviceAttrs) {
        DeviceAttrs = deviceAttrs;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    @JsonProperty("DeviceType")
    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    @JsonProperty("DeviceID")
    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
