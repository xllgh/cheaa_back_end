package com.gizwits.bsh.bean.resvo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gizwits.bsh.bean.BSHDevice;
import com.gizwits.bsh.bean.RetObject;
import com.gizwits.bsh.enums.DeviceStatus;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备状态响应对象
 */
public class DeviceStatusResVO extends RetObject {
    @ApiModelProperty(value = "设备状态", required = true)
    @JsonProperty(value = "Device")
    private List<BSHDevice> devices;

    public DeviceStatusResVO(BSHDevice device) {
        super();
        devices = new ArrayList<>();
        devices.add(device);
    }

    public DeviceStatusResVO(RetObject retObject) {
        this.setRetCode(retObject.getRetCode());
        this.setRetInfo(retObject.getRetInfo());
        this.devices = new ArrayList<>();
    }

    public List<BSHDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<BSHDevice> devices) {
        this.devices = devices;
    }
}
