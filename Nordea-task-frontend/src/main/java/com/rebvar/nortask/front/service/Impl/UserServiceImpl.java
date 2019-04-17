package com.rebvar.nortask.front.service.Impl;


import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.rebvar.nortask.front.AppConstants;
import com.rebvar.nortask.front.service.ServiceHelper;
import com.rebvar.nortask.front.service.UserService;
import com.rebvar.nortask.front.ui.model.request.UserDataRequestModel;
import com.rebvar.nortask.front.ui.model.request.UserLoginRequestModel;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Override
	public String login(UserLoginRequestModel info) {
		String uri = AppConstants.REST_BASE+AppConstants.LOGIN_URL;
	    JSONObject request = new JSONObject();
	    request.put("email", info.getEmail());
	    request.put("password", info.getPassword());
	    HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.POST, request, "");
	    return response.getHeaders().get(AppConstants.HEADER_STRING).toString();
	}
	
	
	@Override
	public String signup(UserDataRequestModel info) {
		String uri = AppConstants.REST_BASE+AppConstants.SIGN_UP_URL;
	    JSONObject request = new JSONObject();
	    request.put("name", info.getName());
	    request.put("email", info.getEmail());
	    request.put("password", info.getPassword());
	    HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.POST, request, "");
    	return response.getBody();
	}


	@Override
	public JSONObject getUserInfo(String token) {
		String uri = AppConstants.REST_BASE+AppConstants.USER_DETAILS_URL;
		JSONObject request = new JSONObject();
		HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.GET, request, token);
				
		String body = response.getBody();
		return new JSONObject(body);
	}

	@Override
	public JSONObject updateUserData(UserDataRequestModel userInfo, String token) {
		String uri = AppConstants.REST_BASE+AppConstants.USER_DETAILS_URL;
		JSONObject request = new JSONObject();
		request.put("email", userInfo.getEmail());
		request.put("name", userInfo.getName());
		request.put("password", "");
		HttpEntity<String> response = ServiceHelper.prepareRestRequest(uri, HttpMethod.PUT, request, token);		
		String body = response.getBody();
		return new JSONObject(body);
	}
	
}
