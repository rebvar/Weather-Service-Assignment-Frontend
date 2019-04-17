package com.rebvar.nortask.front.ui.model.request;

import com.rebvar.nortask.front.common.models.AbstractUserModel;

public class UserLoginRequestModel extends AbstractUserModel {

	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
