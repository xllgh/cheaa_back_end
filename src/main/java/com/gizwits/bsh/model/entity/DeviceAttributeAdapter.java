package com.gizwits.bsh.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhl on 2016/12/5.
 */
@Table(name = "t_device_media_adapter")
public class DeviceAttributeAdapter {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "plat_id")
    private String platId;

    @Column(name = "third_attr")
    private String thirdAttr;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "standard_attr")
    private String standardAttr;

    @Column(name = "adapter_map")
    private String adapterMap;

    @Column(name = "description")
    private String description;

    @Column(name = "reverse_adapter_map")
    private String reverseAdapterMap;

    public String getReverseAdapterMap() {
        return reverseAdapterMap;
    }

    public void setReverseAdapterMap(String reverseAdapterMap) {
        this.reverseAdapterMap = reverseAdapterMap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public Long getId() {
        return id;
    }

    public String getThirdAttr() {
        return thirdAttr;
    }

    public void setThirdAttr(String thirdAttr) {
        this.thirdAttr = thirdAttr;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getStandardAttr() {
        return standardAttr;
    }

    public void setStandardAttr(String standardAttr) {
        this.standardAttr = standardAttr;
    }

    public String getAdapterMap() {
        return adapterMap;
    }

    public void setAdapterMap(String adapterMap) {
        this.adapterMap = adapterMap;
    }
}
