package com.gizwits.bsh.enums;

/**
 * Created by zhl on 2016/11/28.
 */
public enum ErrType {
    SUCCESS(0,"成功"),
    FAIL(-1,"失败"),
    ILLEGAL_PARAM(1,"参数错误"),
    DEVICE_NOTEXISTED(2,"设备不存在"),
    SYSTEM_ERROR(3,"系统异常"),
    DEVICE_EXISTED(4,"用户设备已存在"),
    PLAT_NOTEXISTED(5,"Plat信息不存在,请联系管理员录入"),
    DEVICE_BIND_EXISTED(6,"用户已绑定该设备"),
    DEVICE_BIND_NOTEXISTED(7,"用户未绑定该设备"),
    DEVICE_TYPE_NOTEXISTED(8,"设备类型不存在,请联系管理员录入"),


    WEB_SESSION_TIMEOUT(10000,"会话过期"),
    WEB_ACCOUNT_EXISTED(10002,"账户已存在"),
    WEB_ACCOUNT_NOTEXISTED(10003,"账户不存在"),
    WEB_ACCOUNT_LOCKD(10004,"账户已被锁定,请联系管理员"),
    WEB_ACCOUNT_LOGIN_LIMIT(10005,"登录尝试失败次数过多,一小时后再试"),

    WEB_ILLEGAL_OPTION(30005,"非法操作"),
    WEB_ILLEGAL_ACCOUNT_PASSWORD(30006,"用户名或密码错误"),
    WEB_WRONG_ACCOUNT_PASSWORD(30007,"当前密码不正确");

    Integer errcode;
    String errmsg;

    private ErrType(Integer errcode, String errmsg) {
        this.errcode = errcode;
        this.errmsg = errmsg;
    }
    public Integer getErrcode() {
        return errcode;
    }
    public String getErrmsg() {
        return errmsg;
    }
}
