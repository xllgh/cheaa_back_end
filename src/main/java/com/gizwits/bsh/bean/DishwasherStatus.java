package com.gizwits.bsh.bean;

import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 洗碗机状态
 */
public class DishwasherStatus extends BSHDeviceAttr {
    /**
     * 洗碗机开关机状态
     */
    private String onOffStatus;

    /**
     * 洗碗机门状态
     */
    private String doorStatus;

    /**
     * 洗碗机运行状态
     */
    private String runningStatus;

    /**
     * 洗碗机运行程序
     */
    private String program;

    /**
     * 洗碗机在线状态
     */
    private String connected;

    public DishwasherStatus(List<HomeApplianceStatus> settings, List<HomeApplianceStatus> status, HomeApplianceProgram program) {
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
        if (program != null) {
            switch (program.getProgram()) {
                case HomeApplianceKey.dishwasherProgramAuto2:
                    this.program = "1";
                    break;
                case HomeApplianceKey.dishwasherProgramEco50:
                    this.program = "2";
                    break;
                case HomeApplianceKey.dishwasherProgramQuick45:
                    this.program = "3";
                    break;
                default:
                    this.program = "4";
                    break;
            }
        }

    }

    public ModelMap getModelMap() {
        ModelMap map = new ModelMap();
        map.put("onOffStatus", getOnOffStatus());
        map.put("doorStatus", getDoorStatus());
        map.put("runningStatus", getRunningStatus());
        map.put("program", getProgram());
        map.put("connected", getConnected());
        return map;
    }

    public static String parseProgramKey(ModelMap attr) {
        if (attr.get("program") == null) {
            return null;
        }
        String program = (String) attr.get("program");
        switch (program) {
            case "1":
                return HomeApplianceKey.dishwasherProgramAuto2;
            case "2":
                return HomeApplianceKey.dishwasherProgramEco50;
            case "3":
                return HomeApplianceKey.dishwasherProgramQuick45;
            default:
                return null;
        }
    }

    public String getOnOffStatus() {
        return onOffStatus;
    }

    public void setOnOffStatus(String onOffStatus) {
        this.onOffStatus = onOffStatus;
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus;
    }

    public String getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(String runningStatus) {
        this.runningStatus = runningStatus;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getConnected() {
        return connected;
    }

    public void setConnected(String connected) {
        this.connected = connected;
    }
}
