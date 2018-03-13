package com.gizwits.bsh.bean.reqvo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gizwits.bsh.bean.BSHDevice;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 */
public class BSHDeviceReqVO {
    @JsonProperty(value = "UserID")
    private String userID;

    @JsonProperty(value = "Device")
    private List<BSHDevice> device;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<BSHDevice> getDevice() {
        return device;
    }

    public void setDevice(List<BSHDevice> device) {
        this.device = device;
    }

    /*@JsonIgnore
    public String getLoginID() {
	if(getUserID() == null || getUserID().size() == 0) {
		return null;
	}
	return getUserID().get(0).get(0);
	}*/
    @JsonIgnore
    public String getDeviceType() {
        if (getDevice() == null || getDevice().size() == 0) {
            return null;
        }
        BSHDevice device = getDevice().get(0);
        if (device.getDeviceInfo() == null) {
            return null;
        }
        return device.getDeviceInfo().getDeviceType();
    }

    @JsonIgnore
    public String getDeviceID() {
        if (getDevice() == null || getDevice().size() == 0) {
            return null;
        }
        BSHDevice device = getDevice().get(0);
        if (device.getDeviceInfo() == null) {
            return null;
        }
        return device.getDeviceInfo().getDeviceID();
    }

    @JsonIgnore
    public ModelMap getDeviceAttr() {
        if (getDevice() == null || getDevice().size() == 0) {
            return null;
        }
        BSHDevice device = getDevice().get(0);
        if (device.getDeviceAttr() == null || device.getDeviceAttr().size() == 0) {
            return null;
        }
        return device.getDeviceAttr();
    }
}
