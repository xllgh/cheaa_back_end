package com.gizwits.bsh.bean.reqvo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import com.gizwits.bsh.bean.UserLogin;
import com.gizwits.bsh.bean.UserBase;
public class LoginReqVO{
	@JsonProperty(value="User")
	private List<UserLogin> userLogin;

	public List<UserLogin> getUserLogin(){
		return userLogin;
	}
	

	public void setUserLogin(List<UserLogin> userLogin){
		this.userLogin = userLogin;
}

	@JsonIgnore
	public String getLoginID(){
		if(getUserLogin() == null){
			return null;
		}
		UserBase userBase = userLogin.get(0).getUserBase();
		if(userBase != null && userBase.getLoginID() != null)
		{
			return userBase.getLoginID();
		}
		return null;
	}
}
