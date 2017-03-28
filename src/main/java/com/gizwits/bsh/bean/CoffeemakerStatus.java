package com.gizwits.bsh.bean;

import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 咖啡机状态
 */
public class CoffeemakerStatus extends BSHDeviceAttr {
    /**
     * 咖啡机开关机状态
     */
    private String onOffStatus;

    /**
     * 咖啡机运行状态
     */
    private String runningStatus;

    /**
     * 咖啡机运行程序
     */
    private String program;

    public CoffeemakerStatus(List<HomeApplianceStatus> settings, List<HomeApplianceStatus> status, HomeApplianceProgram program) {
        onOffStatus = falseValue;
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
                case HomeApplianceKey.coffeeMakerProgramEspresso:
                    this.program = "1";
                    break;
                case HomeApplianceKey.coffeeMakerProgramEspressoMacchiato:
                    this.program = "2";
                    break;
                case HomeApplianceKey.coffeeMakerProgramCoffee:
                    this.program = "3";
                    break;
                case HomeApplianceKey.coffeeMakerProgramCappuccino:
                    this.program = "4";
                    break;
                case HomeApplianceKey.coffeeMakerProgramLatteMacchiato:
                    this.program = "5";
                    break;
                case HomeApplianceKey.coffeeMakerProgramCaffeLatte:
                    this.program = "6";
                    break;
                default:
                    this.program = "7";
                    break;
            }
        }
    }

    public ModelMap getModelMap() {
        ModelMap map = new ModelMap();
        map.put("onOffStatus", getOnOffStatus());
        map.put("runningStatus", getRunningStatus());
        map.put("program", getProgram());
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
                return HomeApplianceKey.coffeeMakerProgramEspresso;
            case "2":
                return HomeApplianceKey.coffeeMakerProgramEspressoMacchiato;
            case "3":
                return HomeApplianceKey.coffeeMakerProgramCoffee;
            case "4":
                return HomeApplianceKey.coffeeMakerProgramCappuccino;
            case "5":
                return HomeApplianceKey.coffeeMakerProgramLatteMacchiato;
            case "6":
                return HomeApplianceKey.coffeeMakerProgramCaffeLatte;
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
}
