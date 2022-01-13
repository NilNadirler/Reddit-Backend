package com.example.demo.business.service;



import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailService {

	public UserDetails loadUserByUsername(String name);//optinal User
	
	
	
}
