package com.springboot.login.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.login.rpository.UserDetails;
import com.springboot.login.service.UserServiceData;

@Controller
public class UserController2 {

	
	@Autowired
	private UserServiceData service;

	

	@RequestMapping("/loginREquest")
	public String loadHomePage(@RequestParam("userName") String userName, @RequestParam("password") String password,Model model, RedirectAttributes redirectAttributes) {
		
		UserDetails user=service.validateUser(userName,password);
		
		
		if(user==null)
			return "failure";
		
		else {
			
			return "Sucess"+user.getName();
		
		}		
		
	}
	
	
	
	
	

}
