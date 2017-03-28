package com.gizwits.bsh.model.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_plat")
public class Plat {
    @Id
    private Long id;

    /**
     * A厂商云平台的ID
     */
    @Column(name = "plat_id")
    private String platId;

    /**
     * B厂商云平台提供给A厂商云平台的应用ID，标识第三方应用
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * B厂商云平台颁发的安全令牌。(正常不会过期)
     */
    @Column(name = "refresh_token")
    private String refreshToken;

    /**
     * 使用refresh_token获取的access_token
     */
    @Column(name = "access_token")
    private String accessToken;

    /**
     * 描述
     */
    private String description;

    private String creator;

    @Column(name = "create_time")
    private Date createTime;

    private String updator;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取A厂商云平台的ID
     *
     * @return plat_id - A厂商云平台的ID
     */
    public String getPlatId() {
        return platId;
    }

    /**
     * 设置A厂商云平台的ID
     *
     * @param platId A厂商云平台的ID
     */
    public void setPlatId(String platId) {
        this.platId = platId;
    }

    /**
     * 获取B厂商云平台提供给A厂商云平台的应用ID，标识第三方应用
     *
     * @return app_id - B厂商云平台提供给A厂商云平台的应用ID，标识第三方应用
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置B厂商云平台提供给A厂商云平台的应用ID，标识第三方应用
     *
     * @param appId B厂商云平台提供给A厂商云平台的应用ID，标识第三方应用
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取B厂商云平台颁发的安全令牌。(正常不会过期)
     *
     * @return refresh_token - B厂商云平台颁发的安全令牌。(正常不会过期)
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * 设置B厂商云平台颁发的安全令牌。(正常不会过期)
     *
     * @param refreshToken B厂商云平台颁发的安全令牌。(正常不会过期)
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * 获取使用refresh_token获取的access_token
     *
     * @return access_token - 使用refresh_token获取的access_token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 设置使用refresh_token获取的access_token
     *
     * @param accessToken 使用refresh_token获取的access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
}