package com.gizwits.bsh.bean;

import org.springframework.ui.ModelMap;

import java.util.Map;

/**
 * 设备属性对象（translator接口）
 */
public class BSHDeviceAttr {

    public static final String trueValue = "True";
    public static final String falseValue = "False";

    public String getOnOffStatusFromPowerStateValue(String value) {
        if (value.equals(HomeApplianceKey.PowerStateValue.on)) {
            return trueValue;
        }
        return falseValue;
    }

    public String getDoorStatusFromDoorStateValue(String value) {
        if (value.equals(HomeApplianceKey.DoorStateValue.open)) {
            return trueValue;
        }
        return falseValue;
    }

    public String getRunningStatusFromOperationStateValue(String value) {
        switch (value) {
            case HomeApplianceKey.OperationStateValue.run:
            case HomeApplianceKey.OperationStateValue.actionRequired:
            case HomeApplianceKey.OperationStateValue.error:
            case HomeApplianceKey.OperationStateValue.aborting:
                return  "1";
            case HomeApplianceKey.OperationStateValue.pause:
                return  "2";
            case HomeApplianceKey.OperationStateValue.inactive:
            case HomeApplianceKey.OperationStateValue.ready:
            case HomeApplianceKey.OperationStateValue.delayedStart:
            case HomeApplianceKey.OperationStateValue.finished:
                return  "3";
        }
        return "0";
    }

    public static HomeApplianceStatus parseCommonSetting(ModelMap attr) {
        for (Map.Entry<String, Object> entry : attr.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            if (key.equals("onOffStatus")) {
                if (value.equals(trueValue)) {
                    return new HomeApplianceStatus(HomeApplianceKey.powerState, HomeApplianceKey.PowerStateValue.on);
                } else if (value.equals(falseValue)) {
                    return new HomeApplianceStatus(HomeApplianceKey.powerState, HomeApplianceKey.PowerStateValue.off);
                }
                return null;
            }
        }
        return null;
    }

}
