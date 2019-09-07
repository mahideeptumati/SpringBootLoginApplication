package com.springboot.login.security;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.springboot.login.rpository.UserDetails;

@Component
public class CurrentUser {

	@Autowired
	CurrentUser currentUser;

	public UserDetails getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof UserDetails)		
			return (UserDetails)authentication.getPrincipal();
		else
			return null;
	}

	
	public Object getCurrentUserForLogging() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication!=null && authentication.getPrincipal()!=null) {
			if(authentication.getPrincipal() instanceof UserDetails)
				return authentication.getPrincipal();
			else 
				return null;
		} else 
			return null;
	}
	
	public void removeCurrentUser(HttpServletRequest request, HttpServletResponse response) {

		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();

		//currentUser.getCurrentUser().setUserLoginStatusBean(2);


		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		
		Cookie[] cookies = request.getCookies();
		String cookieName;
		String cookiePath;
		Cookie cookie;
		for(int i =0;i<cookies.length;i++) {
			cookieName = cookies[i].getName();
			cookie = new Cookie(cookieName, "");
			cookiePath = request.getContextPath();
			if (!StringUtils.hasLength(cookiePath)) {
				cookiePath = "/";
			}
			cookie.setPath(cookiePath);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	
		
	}
	
}

