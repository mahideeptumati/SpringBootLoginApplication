package com.springboot.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.login.rpository.UserDetails;
import com.springboot.login.rpository.UserDetailsRepository;

@Service
public class ServiceImplementation implements UserServiceData{

	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	@Override
	public UserDetails validateUser(String name, String password) {

		UserDetails userData=userDetailsRepository.findByNameAndPassword(name, password);
		
			
		
		return userData;
	}



	

}
