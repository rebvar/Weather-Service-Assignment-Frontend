package com.rebvar.nortask.front;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.rebvar.nortask.front.service.UserService;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	UserService userService;
	
    @ModelAttribute
    public void populateUser(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String authCookie, Model model, HttpServletResponse response) {
        
    	if (authCookie.isEmpty())
    	{
    		model.addAttribute("user", null);
    		return;
    	}
    	
    	try
    	{
    		JSONObject user = userService.getUserInfo(authCookie);
    		if (user==null || !user.has("userId") || user.getString("userId").length()<=0)
    		{
        		model.addAttribute("user", null);
        		Cookie cookie = new Cookie(AppConstants.HEADER_STRING,null);
        		cookie.setPath("/");
        		cookie.setMaxAge(0);
        		response.addCookie(cookie);
        		return;
        	}
    		else
    		{
    			model.addAttribute("user", user);
    			return;
    		}
    	}
    	catch(Exception ex)
    	{
    		model.addAttribute("user", null);
    		Cookie cookie = new Cookie(AppConstants.HEADER_STRING,null);
    		cookie.setPath("/");
    		cookie.setMaxAge(0);
    		response.addCookie(cookie);
    		return;
    	}
    }
}