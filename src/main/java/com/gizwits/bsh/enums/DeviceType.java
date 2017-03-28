package com.gizwits.bsh.enums;

/**
 * Created by neil on 2016/11/5.
 */
public enum DeviceType {

    Hood("010404"),//油烟机
    IceBox("010103"),//冰箱
    HVBD("010***"),//空调
    CAFFEE("010****"),//咖啡机
    OVEN("010*****"),//烤箱
    DishWashing("010*****");//洗碗机

    DeviceType(String code){
        this.code = code;
    }

    String code;

    public String getCode() {
        return code;
    }

    public static DeviceType getDeviceType(String code) {

        for (DeviceType deviceType : DeviceType.values()) {
            if (code.equalsIgnoreCase(deviceType.getCode())) {
                return deviceType;
            }
        }
        return null;
    }
}
