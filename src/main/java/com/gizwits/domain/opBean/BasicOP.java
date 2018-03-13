package com.gizwits.domain.opBean;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;


import javax.validation.constraints.NotNull;
/**
 * Created by neil on 2016/11/6.
 */
public class BasicOP {

	//@NotNull
	//@JsonProperty("TargetPlatID")
	//private String targetPlatID;

	@NotNull
	@JsonProperty("UserID")
	private String userID;


	//public String getTargetPlat(){
	//	return targetPlatID;
	//}

	//public void setTargetPlat(String targetPlat){
	//	targetPlatID = targetPlat;
	//}
	
	public void setUser(String UserID){
		userID = UserID;}

	public String getUser(){
		return userID;
	} 
}
