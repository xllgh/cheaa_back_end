package com.gizwits.bsh.bean.resvo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gizwits.bsh.bean.BSHDevice;
import com.gizwits.bsh.bean.HomeAppliance;
import com.gizwits.bsh.bean.RetObject;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备列表响应对象
 */
public class DeviceListResVO extends RetObject {
    @ApiModelProperty(value = "设备列表", required = true)
    @JsonProperty(value = "Device")
    private List<BSHDevice> devices;

    public DeviceListResVO(List<BSHDevice> devices) {
        super();
        this.devices = devices;
    }

    public DeviceListResVO(RetObject retObject) {
        setRetCode(retObject.getRetCode());
        setRetInfo(retObject.getRetInfo());
        this.devices = new ArrayList<>();
    }

    public List<BSHDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<BSHDevice> devices) {
        this.devices = devices;
    }
}
