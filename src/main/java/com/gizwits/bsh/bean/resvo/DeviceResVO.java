package com.gizwits.bsh.bean.resvo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gizwits.bsh.bean.BSHDevice;
import com.gizwits.bsh.bean.HomeAppliance;
import com.gizwits.bsh.bean.RetObject;
import com.wordnik.swagger.annotations.ApiModelProperty;


public class DeviceResVO extends RetObject{
	@ApiModelProperty(value="Device Info", required = true)
	@JsonProperty(value = "Device")
	private BSHDevice device;

	public DeviceResVO(BSHDevice device){
		super();
		this.device = device;
	}
	
	public DeviceResVO(RetObject retObject){
		setRetCode(retObject.getRetCode());
		setRetInfo(retObject.getRetInfo());
		this.device = new BSHDevice();
	}

	public BSHDevice getDevice(){
		return device;
	}
	public void setDevice(BSHDevice device){
		this.device = device;
	}
}
