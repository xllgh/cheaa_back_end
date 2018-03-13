package com.gizwits.bsh.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLogin{
	@JsonProperty(value="UserBase")
	private UserBase userBase;
	
	@JsonProperty(value="UserProfile")
	private UserProfile userProfile;

	public UserLogin(){
		this.userBase = new UserBase();
	}
	public UserBase getUserBase(){
		return userBase;
	}

	public UserProfile getUserProfile(){
		return userProfile;
	}

	public void setUserBase(UserBase userBase){
		this.userBase = userBase;}

	public void setUserProfile(UserProfile userProfile){
		this.userProfile = userProfile;
	}
}

