package com.gizwits.bsh.model.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_user_plat")
public class PlatUser{
    @Id
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    /**
     * 所属云平台ID
     */
    @Column(name = "plat_id")
    private String platId;

    @Column(name = "first_login_time")
    private Date firstLoginTime;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "is_all")
    private Integer isAll;

    public Integer getIsAll() {
        return isAll;
    }

    public void setIsAll(Integer isAll) {
        this.isAll = isAll;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取所属云平台ID
     *
     * @return plat_id - 所属云平台ID
     */
    public String getPlatId() {
        return platId;
    }

    /**
     * 设置所属云平台ID
     *
     * @param platId 所属云平台ID
     */
    public void setPlatId(String platId) {
        this.platId = platId;
    }

    /**
     * @return first_login_time
     */
    public Date getFirstLoginTime() {
        return firstLoginTime;
    }

    /**
     * @param firstLoginTime
     */
    public void setFirstLoginTime(Date firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    /**
     * @return last_login_time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return last_login_ip
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * @param lastLoginIp
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}