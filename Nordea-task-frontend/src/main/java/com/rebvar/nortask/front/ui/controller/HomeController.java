package com.rebvar.nortask.front.ui.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rebvar.nortask.front.AppConstants;

@Controller
@RequestMapping("/")
public class HomeController {

	@GetMapping("/")
	public String index(@CookieValue(value=AppConstants.HEADER_STRING, defaultValue = "") String auth, Model model) {
		
		return "index";
	}
}
