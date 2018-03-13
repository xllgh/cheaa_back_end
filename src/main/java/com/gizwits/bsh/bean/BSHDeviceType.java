package com.gizwits.bsh.bean;

/**
 * 博西设备类型
 */
public enum BSHDeviceType {
    FridgeFreezer("FridgeFreezer"),
    Oven("Oven"),
    CoffeeMaker("CoffeeMaker"),
    Dishwasher("Dishwasher"),
    Washer("Washer");
	
    private final String text;

    BSHDeviceType(final String text) {
        this.text = text;
    }
}
