package com.gizwits.bsh.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserBase{
	private String loginID;
	private String loginName;
	private String email;
	private String mobile;
	private String userID;

	public UserBase(){
		this.userID = "cheaashowcase2018@mailinator.com";		
	}

	public String getLoginID(){return loginID;}
	public String getLoginName(){return loginName;}
	public String getEmail(){return email;}
	public String getMobile(){return mobile;}
	public String getUserID(){return userID;}

	public void setUserID(String userID){
		this.userID = userID;
	}

	public void setLoginID(String loginID){
		this.loginID = loginID;}

	public void setLoginName(String loginName){
		this.loginName = loginName;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
}
