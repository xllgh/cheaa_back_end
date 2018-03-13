package com.gizwits.bsh.bean;

public class UserProfile{
	private String desc;
	private String nickName;
	private String address;
	private String gender;
	private String userName;
	
	public UserProfile(){}
	
	public String getDesc(){return desc;}
	public String getNickName(){return nickName;}
	public String getAddress(){return address;}
	public String getGender(){return gender;}
	public String getUserName(){return userName;}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

}
