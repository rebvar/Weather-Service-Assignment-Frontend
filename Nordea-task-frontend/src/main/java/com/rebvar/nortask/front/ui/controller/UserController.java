package com.rebvar.nortask.front.ui.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.rebvar.nortask.front.AppConstants;
import com.rebvar.nortask.front.service.UserService;
import com.rebvar.nortask.front.ui.model.request.UserDataRequestModel;
import com.rebvar.nortask.front.ui.model.request.UserLoginRequestModel;
import com.rebvar.nortask.front.ui.model.response.UserDataResponseModel;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(path = "/users",method = RequestMethod.GET)
	public String getUserDetails(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,Model model) {
		if (authCookie.isEmpty())
		{
			return "redirect:/";
		}
		
		UserDataResponseModel userInfo = new UserDataResponseModel();
		JSONObject user = userService.getUserInfo(authCookie);
		userInfo.setEmail(user.getString("email"));
		userInfo.setName(user.getString("name"));
		userInfo.setUserId(user.getString("userId"));
		
		model.addAttribute("userInfo", userInfo);
		
		return "user/details";
	}
	
	@RequestMapping(path = "/users",method = RequestMethod.POST)
	public String updateUserDetails(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,Model model, UserDataRequestModel userData) {
		
		if (authCookie.isEmpty())
		{
			return "redirect:/";
		}
		
		try
		{
			userService.updateUserData(userData ,authCookie);
		}
		catch (Exception e) {
			model.addAttribute("error", "Update User Failed.."+e.toString());
		}
		
		return "redirect:/users";
	}
	
	
	@RequestMapping(path = "/signin",method = RequestMethod.GET)
	public String signin(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,Model model) {
		if (!authCookie.isEmpty())
		{
			return "redirect:/";
		}
		
		model.addAttribute("userLogin", new UserLoginRequestModel());
        return "user/signin";
    }
	
	
	
	@RequestMapping(path = "/signin",method = RequestMethod.POST)
	public String signin(HttpServletResponse response, UserLoginRequestModel loginInfo, Model model) {	
		try
		{
			String auth;
			auth = userService.login(loginInfo);
			auth =auth.replace("[", "").replace("]", "");
			Cookie cookie = new Cookie(AppConstants.HEADER_STRING,auth);
			cookie.setPath("/");
			cookie.setMaxAge(86400);
			response.addCookie(cookie);
		    return "redirect:/";
		}
		catch(Exception ex)
		{
			model.addAttribute("userLogin",loginInfo);
			model.addAttribute("error","Error signining in... "+ex.toString());
			return "user/signin";
		}
		

    }
	
	
	
	
	
	@RequestMapping(path = "/signout",method = RequestMethod.GET)
	public String signout(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,HttpServletResponse response) {	
		Cookie cookie = new Cookie(AppConstants.HEADER_STRING,null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
	
	
	@RequestMapping(path = "/signup",method = RequestMethod.GET)
	public String signup(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie,Model model) {
		if (!authCookie.isEmpty())
		{
			return "redirect:/";
		}
		
		model.addAttribute("userSignup", new UserDataRequestModel());
        return "user/signup";
    }
	
	@RequestMapping(path = "/signup",method = RequestMethod.POST)
	public String signup(HttpServletResponse response, UserDataRequestModel signupInfo, Model model) {	
		try
		{
			userService.signup(signupInfo);
			return "redirect:/signin";
		}
		catch(Exception ex)
		{
			model.addAttribute("userSignup", signupInfo);
			model.addAttribute("error","Signup failded..."+ex.toString());
			return "user/signup";
		}
    }
	
}
