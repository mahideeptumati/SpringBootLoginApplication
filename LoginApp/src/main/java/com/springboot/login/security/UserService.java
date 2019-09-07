package com.springboot.login.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.springboot.login.rpository.UserDetails;


public class UserService extends SimpleUrlLogoutSuccessHandler {
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if (authentication != null) {

			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails userInfo = (UserDetails) authentication.getPrincipal();
				if (userInfo != null)

				{
		}

		super.onLogoutSuccess(request, response, authentication);
	}
	}
	}}

