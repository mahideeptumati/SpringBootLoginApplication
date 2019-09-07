package com.springboot.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.springboot.login.rpository.UserDetails;
import com.springboot.login.rpository.UserDetailsRepository;





public class AuthenticationProviderClass implements AuthenticationProvider {

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			
			UserDetails user = (UserDetails) authentication.getPrincipal();
			UserDetails userObj = validateUserInfo(user);
			return new UsernamePasswordAuthenticationToken(userObj, userObj.getPassword());

			// return authentication;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	private UserDetails validateUserInfo(UserDetails user) {

		String userId = user.getName();
		String password = user.getPassword();

		UserDetails userAccInfoFinalObj = userDetailsRepository.findByNameAndPassword(userId, password);

		if (userAccInfoFinalObj == null) {
			throw new BadCredentialsException("User  not exists.");

		}
		
		else{
			
		}

/*		user.setUserLoginStat(AssistConstantsParameters.USER_LOGGED_IN_CURRENTLY);
*/
		return userAccInfoFinalObj;

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
