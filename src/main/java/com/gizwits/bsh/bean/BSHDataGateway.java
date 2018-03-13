package com.gizwits.bsh.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.ui.ModelMap;


public class BSHDataGateway{
	@JsonProperty(value="TimeStamp")
	private String timeStamp;

	@JsonProperty(value="DeviceID")
	private String deviceID;

	@JsonProperty(value="DataType")
	private String dataType;
	
	@JsonProperty(value="DeviceStatus")
	private ModelMap deviceStatus;
	
	public BSHDataGateway(){
		this.timeStamp = createTimeStamp();
		this.dataType = "attr";	
	}
	
	public BSHDataGateway(String deviceID, ModelMap deviceStatus){
		this.timeStamp = createTimeStamp();
		this.deviceID = deviceID;
		this.dataType = "attr";
		this.deviceStatus = deviceStatus;
}
	private String createTimeStamp(){
		return System.currentTimeMillis() + 28800000 + "";}

	public void setDeviceID(String deviceID){
		this.deviceID = deviceID;}

	public String getDeviceID(){return deviceID;}

	public void setDeviceStatus(ModelMap deviceStatus){this.deviceStatus = deviceStatus;}

	public ModelMap getDeviceStatus(){return deviceStatus;}

}
