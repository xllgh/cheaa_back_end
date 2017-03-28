package com.gizwits.bsh.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "t_device")
public class Device {
    @Id
    private Long id;

    /**
     * 设备ID
     */
    @Column(name = "device_id")
    private String deviceId;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 所属用户ID
     */
    @Column(name = "owner_id")
    private String ownerId;

    /**
     * 所属云平台
     */
    @Column(name = "plat_id")
    private String platId;

    /**
     * 是否可用：
0，不可用；1，可用
     */
    @Column(name = "is_avail")
    private String isAvail;

    private String creator;

    @Column(name = "create_time")
    private Date createTime;

    private String updator;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "device_type")
    private String deviceType;

    private Boolean online;

    @Transient
    private String appId;

    @Transient
    private String accessToken;

    @Transient
    private String factory;

    @Transient
    private String imageUri;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取设备ID
     *
     * @return device_id - 设备ID
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 设置设备ID
     *
     * @param deviceId 设备ID
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取设备名称
     *
     * @return name - 设备名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置设备名称
     *
     * @param name 设备名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取所属用户ID
     *
     * @return owner_id - 所属用户ID
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * 设置所属用户ID
     *
     * @param ownerId 所属用户ID
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * 获取所属云平台
     *
     * @return plat_id - 所属云平台
     */
    public String getPlatId() {
        return platId;
    }

    /**
     * 设置所属云平台
     *
     * @param platId 所属云平台
     */
    public void setPlatId(String platId) {
        this.platId = platId;
    }

    /**
     * 获取是否可用：
0，不可用；1，可用
     *
     * @return is_avail - 是否可用：
0，不可用；1，可用
     */
    public String getIsAvail() {
        return isAvail;
    }

    /**
     * 设置是否可用：
0，不可用；1，可用
     *
     * @param isAvail 是否可用：
0，不可用；1，可用
     */
    public void setIsAvail(String isAvail) {
        this.isAvail = isAvail;
    }

    /**
     * @return creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updator
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * @param updator
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}