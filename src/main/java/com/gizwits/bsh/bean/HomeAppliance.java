package com.gizwits.bsh.bean;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

/**
 * 博西设备
 */
@ApiModel(description = "博西设备对象")
public class HomeAppliance {
    // User-friendly name of the home appliance (e.g. “My Oven”)
    @ApiModelProperty(value = "设备名称", required = true)
    private String name;

    // Brand of the home appliance (e.g. “BOSCH”, “SIEMENS”)
    @ApiModelProperty(value = "品牌", required = true)
    private String brand;

    // Vertriebs-Identifikations-Bezeichner of the home appliance
    @ApiModelProperty(value = "VIB", required = true)
    private String vib;

    // YES if home appliance is connected to Home Appliance Server / Simulator, NO otherwise
    @ApiModelProperty(value = "连接状态", required = true)
    private Boolean connected;

    // Type of the home appliance (e.g. “Oven”, “Dishwasher”)
    @ApiModelProperty(value = "设备类型", required = true)
    private String type;

    // E-Number (VIB/KI) of the home appliance
    @ApiModelProperty(value = "E-Number", required = true)
    private String enumber;

    // Unique home appliance ID
    @ApiModelProperty(value = "设备ID", required = true)
    private String haId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        brand = StringUtils.capitalize(brand.toLowerCase());
        this.brand = brand;
    }

    public String getVib() {
        return vib;
    }

    public void setVib(String vib) {
        this.vib = vib;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getHaId() {
        return haId;
    }

    public void setHaId(String haId) {
        this.haId = haId;
    }
}
