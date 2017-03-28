package com.gizwits.bsh.ream;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by zhl on 2016/12/23.
 */
public class UserPwdTypeToken extends UsernamePasswordToken {
    private Integer accType;
    public UserPwdTypeToken(){

    }

    public UserPwdTypeToken(Integer accType) {
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, char[] password, Integer accType) {
        super(username, password);
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, String password, Integer accType) {
        super(username, password);
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, char[] password, String host, Integer accType) {
        super(username, password, host);
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, String password, String host, Integer accType) {
        super(username, password, host);
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, char[] password, boolean rememberMe, Integer accType) {
        super(username, password, rememberMe);
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, String password, boolean rememberMe, Integer accType) {
        super(username, password, rememberMe);
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, char[] password, boolean rememberMe, String host, Integer accType) {
        super(username, password, rememberMe, host);
        this.accType = accType;
    }

    public UserPwdTypeToken(String username, String password, boolean rememberMe, String host, Integer accType) {
        super(username, password, rememberMe, host);
        this.accType = accType;
    }

    public Integer getAccType() {
        return accType;
    }

    public void setAccType(Integer accType) {
        this.accType = accType;
    }
}
