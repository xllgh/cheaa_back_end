package com.gizwits.bsh.bean;

import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 烤箱状态
 */
public class OvenStatus extends BSHDeviceAttr {

    /**
     * 烤箱开关机状态 BSH.Common.Setting.PowerState
     */
    private String onOffStatus;

    /**
     * 烤箱门状态 BSH.Common.Status.DoorState
     */
    private String doorStatus;

    /**
     * 烤箱运行状态 BSH.Common.Status.OperationState
     * BSH.Common.EnumType.OperationState.Aborting, Error, ActionRequired, Run -> 1 启动
     * BSH.Common.EnumType.OperationState.Pause -> 2 暂停
     * BSH.Common.EnumType.OperationState.Inactive, Ready, DelayedStart, Finished -> 3 完成
     */
    private String runningStatus;

    /**
     * 烤箱运行程序
     * Cooking.Oven.Program.HeatingMode.HotAir -> 1
     * Cooking.Oven.Program.HeatingMode.TopBottomHeating -> 2
     * Cooking.Oven.Program.HeatingMode.PizzaSetting -> 3
     */
    private String program;

    private String temperature;

    private String duration;

    public OvenStatus(List<HomeApplianceStatus> settings, List<HomeApplianceStatus> status, HomeApplianceProgram program) {
        onOffStatus = falseValue;
        doorStatus = falseValue;
        runningStatus = "0";
        List<HomeApplianceStatus> attr = new ArrayList<>();
        attr.addAll(settings);
        attr.addAll(status);
        for (HomeApplianceStatus homeApplianceStatus : attr) {
            String key = homeApplianceStatus.getKey();
            String value = homeApplianceStatus.getValue();
            switch (key) {
                case HomeApplianceKey.powerState:
                    onOffStatus = getOnOffStatusFromPowerStateValue(value);
                    break;
                case HomeApplianceKey.doorState:
                    doorStatus = getDoorStatusFromDoorStateValue(value);
                    break;
                case HomeApplianceKey.operationState:
                    runningStatus = getRunningStatusFromOperationStateValue(value);
                    break;
                default:
                    break;
            }
        }

        this.program = "0";
        temperature = "0";
        duration = "0";
        if (program != null) {
            switch (program.getProgram()) {
                case HomeApplianceKey.ovenProgramHotAir:
                    this.program = "1";
                    break;
                case HomeApplianceKey.ovenProgramTopBottomHeating:
                    this.program = "2";
                    break;
                case HomeApplianceKey.ovenProgramPizzaSetting:
                    this.program = "3";
                    break;
                default:
                    this.program = "4";
                    break;
            }
            for (HomeApplianceStatus option : program.getOptions()) {
                switch (option.getKey()) {
                    case HomeApplianceKey.ovenProgramOptionTemperature:
                        temperature = option.getValue();
                        break;
                    case HomeApplianceKey.ovenProgramOptionDuration:
                        duration = option.getValue();
                        break;
                    default:
                        break;
                }
            }
        }

    }

    public ModelMap getModelMap() {
        ModelMap map = new ModelMap();
        map.put("onOffStatus", getOnOffStatus());
        map.put("doorStatus", getDoorStatus());
        map.put("runningStatus", getRunningStatus());
        map.put("program", getProgram());
        map.put("temperature", getTemperature());
        map.put("duration", getDuration());
        return map;
    }

    public static HomeApplianceStatus parseCommonSetting(ModelMap attr) {
        for (Map.Entry<String, Object> entry : attr.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            if (key.equals("onOffStatus")) {
                if (value.equals(trueValue)) {
                    return new HomeApplianceStatus(HomeApplianceKey.powerState, HomeApplianceKey.PowerStateValue.on);
                } else if (value.equals(falseValue)) {
                    return new HomeApplianceStatus(HomeApplianceKey.powerState, HomeApplianceKey.PowerStateValue.standby);
                }
                return null;
            }
        }
        return null;
    }

    public static String parseProgramKey(ModelMap attr) {
        if (attr.get("program") == null) {
            return null;
        }
        String program = (String) attr.get("program");
        switch (program) {
            case "1":
                return HomeApplianceKey.ovenProgramHotAir;
            case "2":
                return HomeApplianceKey.ovenProgramTopBottomHeating;
            case "3":
                return HomeApplianceKey.ovenProgramPizzaSetting;
            default:
                return null;
        }
    }

    public static List<HomeApplianceStatus> parseProgramOptions(ModelMap attr) {
        List<HomeApplianceStatus> options = new ArrayList<>();
        for (Map.Entry<String, Object> entry : attr.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            switch (key) {
                case "temperature":
                    options.add(new HomeApplianceStatus(HomeApplianceKey.ovenProgramOptionTemperature, value));
                    break;
                case "duration":
                    options.add(new HomeApplianceStatus(HomeApplianceKey.ovenProgramOptionDuration, value));
                    break;
                default:
                    break;
            }
        }
        return options;
    }

    public String getOnOffStatus() {
        return onOffStatus;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public String getRunningStatus() {
        return runningStatus;
    }

    public String getProgram() {
        return program;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDuration() {
        return duration;
    }
}
