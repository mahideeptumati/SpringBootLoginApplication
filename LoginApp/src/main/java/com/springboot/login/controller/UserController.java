package com.springboot.login.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.login.rpository.UserDetails;
import com.springboot.login.security.CurrentUser;

@RestController
public class UserController {

	
	@Autowired
	private CurrentUser currentUser;

	

	@RequestMapping("/")
	public String loadHomePage(Model model, RedirectAttributes redirectAttributes) {
		
		UserDetails userData = currentUser.getCurrentUser();

		return userData.getName();
		
		
	}
	
	
	
	
	

}
