package com.rebvar.nortask.front.service;

import org.json.JSONObject;

import com.rebvar.nortask.front.ui.model.request.UserDataRequestModel;
import com.rebvar.nortask.front.ui.model.request.UserLoginRequestModel;

public interface UserService {

	public String login(UserLoginRequestModel info);
	public String signup(UserDataRequestModel info);
	public JSONObject getUserInfo(String token);
	public JSONObject updateUserData(UserDataRequestModel userInfo, String token);
	
}
