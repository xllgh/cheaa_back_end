package com.gizwits.domain.opBean;

/**
 * Created by neil on 2016/11/6.
 */
public class OPIceBox implements BasicOP {

    private String QuickFreezingMode;//	String	"false"	速冻模式控制 "true"
    private String RefrigeratorTargetTemperature;//	String	"2"~"8"	设置冷藏室温度
    private String FreezerTargetTemperature;//	String	"-25"~"-16"	设置冷冻室温度
    private String VariationTargetTemperature;//	String	"-20"~"-5"	设置变温区温度

    public String getQuickFreezingMode() {
        return QuickFreezingMode;
    }

    public void setQuickFreezingMode(String quickFreezingMode) {
        QuickFreezingMode = quickFreezingMode;
    }

    public String getRefrigeratorTargetTemperature() {
        return RefrigeratorTargetTemperature;
    }

    public void setRefrigeratorTargetTemperature(String refrigeratorTargetTemperature) {
        RefrigeratorTargetTemperature = refrigeratorTargetTemperature;
    }

    public String getFreezerTargetTemperature() {
        return FreezerTargetTemperature;
    }

    public void setFreezerTargetTemperature(String freezerTargetTemperature) {
        FreezerTargetTemperature = freezerTargetTemperature;
    }

    public String getVariationTargetTemperature() {
        return VariationTargetTemperature;
    }

    public void setVariationTargetTemperature(String variationTargetTemperature) {
        VariationTargetTemperature = variationTargetTemperature;
    }
}
