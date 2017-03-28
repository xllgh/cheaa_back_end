package com.gizwits.bsh.enums;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 设备状态
 */
@ApiModel(description = "设备状态")
public class DeviceStatus {

    @ApiModelProperty(value = "设备状态名称", required = true)
    private String name;

    @ApiModelProperty(value = "设备状态值", required = true)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
