package com.springboot.login.service;

import com.springboot.login.rpository.UserDetails;


public interface UserServiceData {

	UserDetails validateUser(String name, String password);
}
