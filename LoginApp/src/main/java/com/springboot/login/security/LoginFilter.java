package com.springboot.login.security;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.login.rpository.UserDetails;
import com.springboot.login.rpository.UserDetailsRepository;




public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private UserService userService;

	@Autowired
	UserDetailsRepository userDetailsRepository;

	

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			if (!request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
			}

			String userName = request.getParameter("userName");
			String password = Base64.getEncoder().encodeToString(request.getParameter("password").getBytes());
			if (userName == null) {
				userName = "";
			}

			if (password == null) {
				password = "";
			}

			userName = userName.trim();

			UsernamePasswordAuthenticationToken authRequest=processUserLogin(userName, password);
			setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	
	
	public UsernamePasswordAuthenticationToken processUserLogin(String userName, String passWord)
			throws AuthenticationException {

		UserDetails userDetailInfo1 = userDetailsRepository.findByNameAndPassword(userName, passWord);
		if (userDetailInfo1 == null) {
			throw new BadCredentialsException("User  not exists.");

		}


		List<GrantedAuthority> userRoles = new ArrayList<GrantedAuthority>();

		return new UsernamePasswordAuthenticationToken(userDetailInfo1, passWord, userRoles);

	}


}
