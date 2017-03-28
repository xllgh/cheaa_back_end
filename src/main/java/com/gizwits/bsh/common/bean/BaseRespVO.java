package com.gizwits.bsh.common.bean;

import com.gizwits.bsh.enums.ErrType;

/**
 * Created by zhl on 2016/12/23.
 */
public class BaseRespVO {
    private Integer code;
    private String msg;

    public BaseRespVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseRespVO(ErrType errType){
        this.code = errType.getErrcode();
        this.msg = errType.getErrmsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
