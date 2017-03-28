package com.gizwits.bsh.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ui.ModelMap;

/**
 * 设备对象（translator接口）
 */
public class BSHDevice {
    @JsonProperty(value = "DeviceInfo")
    private BSHDeviceInfo deviceInfo;

    @JsonProperty(value = "DeviceAttr")
    private ModelMap deviceAttr;

    public BSHDeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(BSHDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public ModelMap getDeviceAttr() {
        return deviceAttr;
    }

    public void setDeviceAttr(ModelMap deviceAttr) {
        this.deviceAttr = deviceAttr;
    }
}
