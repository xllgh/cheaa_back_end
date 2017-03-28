package com.gizwits.bsh.model.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zhl on 2016/11/30.
 */
@Table(name = "t_device_type")
public class DeviceType{
    @Id
    private Long id;

    @Column(name = "plat_id")
    private String platId;

    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "type_name")
    private String typeName;

    @Column(name = "bind_image_uri")
    private String bindImageUri;

    @Column(name = "list_image_uri")
    private String listImageUri;

    @Column(name = "control_image_uri")
    private String controlImageUri;

    @Column(name = "del")
    private Integer del;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "description")
    private String description;

    public DeviceType() {
    }

    public DeviceType(String platId, String typeCode) {
        this.platId = platId;
        this.typeCode = typeCode;
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

    public String getBindImageUri() {
        return bindImageUri;
    }

    public void setBindImageUri(String bindImageUri) {
        this.bindImageUri = bindImageUri;
    }

    public String getListImageUri() {
        return listImageUri;
    }

    public void setListImageUri(String listImageUri) {
        this.listImageUri = listImageUri;
    }

    public String getControlImageUri() {
        return controlImageUri;
    }

    public void setControlImageUri(String controlImageUri) {
        this.controlImageUri = controlImageUri;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
