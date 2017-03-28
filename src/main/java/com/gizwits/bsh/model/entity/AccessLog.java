package com.gizwits.bsh.model.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_access_log")
public class AccessLog {
    @Id
    private Long id;

    @Column(name = "user_id")
    private String userId;

    /**
     * 平台ID
     */
    @Column(name = "plat_id")
    private String platId;

    /**
     * 操作消息体
     */
    @Column(name = "body_data")
    private String bodyData;

    /**
     * 操作请求
     */
    private String action;

    /**
     * 操作时间
     */
    @Column(name = "access_time")
    private Date accessTime;

    /**
     * 操作IP
     */
    @Column(name = "access_ip")
    private String accessIp;

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
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取平台ID
     *
     * @return plat_id - 平台ID
     */
    public String getPlatId() {
        return platId;
    }

    /**
     * 设置平台ID
     *
     * @param platId 平台ID
     */
    public void setPlatId(String platId) {
        this.platId = platId;
    }

    /**
     * 获取操作消息体
     *
     * @return body_data - 操作消息体
     */
    public String getBodyData() {
        return bodyData;
    }

    /**
     * 设置操作消息体
     *
     * @param bodyData 操作消息体
     */
    public void setBodyData(String bodyData) {
        this.bodyData = bodyData;
    }

    /**
     * 获取操作请求
     *
     * @return action - 操作请求
     */
    public String getAction() {
        return action;
    }

    /**
     * 设置操作请求
     *
     * @param action 操作请求
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 获取操作时间
     *
     * @return access_time - 操作时间
     */
    public Date getAccessTime() {
        return accessTime;
    }

    /**
     * 设置操作时间
     *
     * @param accessTime 操作时间
     */
    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    /**
     * 获取操作IP
     *
     * @return access_ip - 操作IP
     */
    public String getAccessIp() {
        return accessIp;
    }

    /**
     * 设置操作IP
     *
     * @param accessIp 操作IP
     */
    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
}