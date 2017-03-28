package com.gizwits.bsh.bean;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.enums.ErrType;

/**
 * Created by neil on 2016/11/5.
 */
public class ResultMap<T> {

    private boolean flag;
    private Integer code;
    private String msg;
    private T data;
    public ResultMap(){}

    public ResultMap(boolean flag, String msg, T data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }
    public ResultMap(ErrType errType){
        this.flag = errType.getErrcode()==0;
        this.msg = errType.getErrmsg();
        this.code = errType.getErrcode();
    }
    public ResultMap(ErrType errType,T data){
        this.flag = errType.getErrcode()==0;
        this.msg = errType.getErrmsg();
        this.data = data;
        this.code = errType.getErrcode();
    }

    public void setErrType(ErrType errType){
        this.flag = errType.getErrcode()==0;
        this.msg = errType.getErrmsg();
        this.code = errType.getErrcode();
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResultMap{" +
                "flag=" + flag +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + (data==null?data:data.toString()) +
                '}';
    }
}
