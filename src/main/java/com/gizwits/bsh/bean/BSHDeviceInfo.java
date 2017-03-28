package com.gizwits.bsh.bean;

/**
 * 设备信息对象（translator接口）
 */
public class BSHDeviceInfo {
    private String deviceID;
    private String deviceType;
    private String model;
    private String manufacturer; // "BSH"
    private String brand; // "SIEMENS"
    private String accessWay; // "1"

    public BSHDeviceInfo() {

    }

    public BSHDeviceInfo(HomeAppliance homeAppliance) {
        deviceType = "99";
        manufacturer = "BSH";
        accessWay = "1";
        deviceID = homeAppliance.getHaId();
        model = homeAppliance.getVib();
        brand = homeAppliance.getBrand();
        if ("FridgeFreezer".equalsIgnoreCase(homeAppliance.getType())) {
            deviceType = "01";
        } else if ("Oven".equalsIgnoreCase(homeAppliance.getType())) {
            deviceType = "04";
        } else if ("Dishwasher".equalsIgnoreCase(homeAppliance.getType())) {
            deviceType = "04";
        } else if ("CoffeeMaker".equalsIgnoreCase(homeAppliance.getType())) {
            deviceType = "04";
        }
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAccessWay() {
        return accessWay;
    }

    public void setAccessWay(String accessWay) {
        this.accessWay = accessWay;
    }
}
