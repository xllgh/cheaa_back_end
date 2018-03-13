package com.gizwits.bsh.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wordnik.swagger.annotations.ApiModelProperty;


/**
 * 接口返回对象
 */
public class RetObject {
    @ApiModelProperty(required = true, notes = "接口的返回码，00000代表成功，其他为失败")
    @JSONField(name = "RetCode")
    @JsonProperty(value = "RetCode")
    private String retCode; //接口的返回码

    @ApiModelProperty(required = true, notes = "接口的返回信息，描述处理结果")
    @JSONField(name = "RetInfo")
    @JsonProperty(value = "RetInfo")
    private String retInfo; //接口的返回信息，描述处理结果

    public JSONObject jsonObject() {
        JSONObject json = (JSONObject) JSON.toJSON(this);
        return json;
    }

    /**
     * 增加额外属性
     * @param key
     * @param object
     * @return
     */
    public JSONObject put(String key, Object object) {
        JSONObject json = (JSONObject) JSON.toJSON(this);
        json.put(key, object);
        return json;
    }

    public RetObject(String retCode, String retInfo) {
        this.retCode = retCode;
        this.retInfo = retInfo;
    }

    public RetObject(){
        this.retCode = "200";
        this.retInfo = "成功";
    }

    public static RetObject success() {
        return new RetObject();
    }

    public static RetObject fail() {
        return new RetObject("500", "失败");
    }

    public static RetObject deviceNotExist() {
        return new RetObject("500", "设备不存在");
    }

    public static RetObject tokenError() {
        return new RetObject("500", "用户验证失败");
    }
    public static RetObject init(){
	return new RetObject("000", "Code init failed");
    }
    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

}
