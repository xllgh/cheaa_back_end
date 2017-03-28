package com.gizwits.bsh.bean;

import com.alibaba.fastjson.JSONObject;

/**
 * 博西设备状态
 */

public class HomeApplianceStatus {

    private String key;
    private String value;

    public HomeApplianceStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public JSONObject toOperationObject() {
        JSONObject object = new JSONObject();
        object.put("key", key);

        switch (key) {
            // Integer
            case HomeApplianceKey.settingFreezerTemperature:
            case HomeApplianceKey.settingFridgeTemperature:
            case HomeApplianceKey.ovenProgramOptionTemperature:
            case HomeApplianceKey.ovenProgramOptionDuration:
                object.put("value", Integer.valueOf(value));
                break;
            // Boolean
            case HomeApplianceKey.settingFreezerSuperMode:
            case HomeApplianceKey.settingFridgeSuperMode:
                object.put("value", Boolean.valueOf(value));
                break;
            // String
            default:
                object.put("value", value);
                break;
        }
        return object;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
