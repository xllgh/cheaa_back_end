package com.gizwits.bsh.bean.resvo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gizwits.bsh.bean.RetObject;
import com.wordnik.swagger.annotations.ApiModelProperty;
import com.gizwits.bsh.bean.UserLogin;
import java.util.List;
import java.util.ArrayList;
public class LoginResVO extends RetObject{
	@ApiModelProperty(value="Login", required = true)
	@JsonProperty(value = "User")
	private List<UserLogin> userLogin;

	public LoginResVO(){
		super();
		userLogin = new ArrayList<>();
		UserLogin userlogin = new UserLogin();
		userLogin.add(userlogin);
	}

	public LoginResVO(RetObject retObject){
		this.setRetCode(retObject.getRetCode());
		this.setRetInfo(retObject.getRetInfo());
		this.userLogin = new ArrayList<>();
	}

	
	public List<UserLogin> getUserLogin(){
		return userLogin;
	}

	public void setUserLogin(List<UserLogin> userLogin){
		this.userLogin = userLogin;
	}
}
