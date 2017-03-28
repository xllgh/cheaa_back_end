package com.gizwits.bsh.bean;

import com.gizwits.bsh.service.impl.HomeConnectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 冰箱状态
 */
public class FridgeFreezerStatus extends BSHDeviceAttr {

    private static Logger logger = LoggerFactory.getLogger(FridgeFreezerStatus.class);

    private String quickCoolingMode;
    private String quickFreezingMode;
    private String refrigeratorTargetTemperature;
    private String freezerTargetTemperature;
    private String refrigeratorTemperature;
    private String freezerTemperature;
    private String doorStatus;

    public FridgeFreezerStatus(List<HomeApplianceStatus> settings, List<HomeApplianceStatus> status) {
        quickCoolingMode = falseValue;
        quickFreezingMode = falseValue;
        refrigeratorTargetTemperature = "0";
        freezerTargetTemperature = "0";
        refrigeratorTemperature = "0";
        freezerTemperature = "0";
        doorStatus = "";

        List<HomeApplianceStatus> attr = new ArrayList<>();
        attr.addAll(settings);
        attr.addAll(status);
        for (HomeApplianceStatus homeApplianceStatus : attr) {
            String key = homeApplianceStatus.getKey();
            String value = homeApplianceStatus.getValue();
            switch (key) {
                case HomeApplianceKey.settingFridgeSuperMode:
                    if (value.equalsIgnoreCase(trueValue)) {
                        quickCoolingMode = trueValue;
                    }
                    break;
                case HomeApplianceKey.settingFreezerSuperMode:
                    if (value.equalsIgnoreCase(trueValue)) {
                        quickFreezingMode = trueValue;
                    }
                    break;
                case HomeApplianceKey.settingFridgeTemperature:
                    refrigeratorTargetTemperature = value;
                    break;
                case HomeApplianceKey.settingFreezerTemperature:
                    freezerTargetTemperature = value;
                    break;
                case HomeApplianceKey.doorState:
                    doorStatus = getDoorStatusFromDoorStateValue(value);
                    break;
            }
        }
    }

    public ModelMap getModelMap() {
        ModelMap map = new ModelMap();
        map.put("quickCoolingMode", getQuickCoolingMode());
        map.put("quickFreezingMode", getQuickFreezingMode());
        map.put("refrigeratorTargetTemperature", getRefrigeratorTargetTemperature());
        map.put("freezerTargetTemperature", getFreezerTargetTemperature());
        map.put("refrigeratorTemperature", getRefrigeratorTemperature());
        map.put("freezerTemperature", getFreezerTemperature());
        map.put("doorStatus", getDoorStatus());
        return map;
    }

    public static HomeApplianceStatus parseOperation(ModelMap operationObject) {
        for (Map.Entry<String, Object> entry : operationObject.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();

            switch (key) {
                case "quickCoolingMode":
                    if (value.equals(trueValue) || value.equals(falseValue)) {
                        return new HomeApplianceStatus(HomeApplianceKey.settingFridgeSuperMode, value);
                    } else {
                        return null;
                    }
                case "quickFreezingMode":
                    if (value.equals(trueValue) || value.equals(falseValue)) {
                        return new HomeApplianceStatus(HomeApplianceKey.settingFreezerSuperMode, value);
                    } else {
                        return null;
                    }
                case "refrigeratorTargetTemperature":
                    return new HomeApplianceStatus(HomeApplianceKey.settingFridgeTemperature, value);
                case "freezerTargetTemperature":
                    return new HomeApplianceStatus(HomeApplianceKey.settingFreezerTemperature, value);
                default:
                    break;
            }
        }
        return null;
    }

    private String getQuickCoolingMode() {
        return quickCoolingMode;
    }

    private String getQuickFreezingMode() {
        return quickFreezingMode;
    }

    private String getRefrigeratorTargetTemperature() {
        return refrigeratorTargetTemperature;
    }

    private String getFreezerTargetTemperature() {
        return freezerTargetTemperature;
    }

    private String getRefrigeratorTemperature() {
        return refrigeratorTemperature;
    }

    private String getFreezerTemperature() {
        return freezerTemperature;
    }

    private String getDoorStatus() {
        return doorStatus;
    }

}
