package com.example.demo.business.service;


import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.model.dto.AuthenticationResponse;
import com.example.demo.model.dto.LoginRequest;
import com.example.demo.model.dto.RegisterRequest;

public interface AuthService {

	public void signUp(RegisterRequest registerRequest);// generateVerificationToken user icinde//mail service
	
	public User getCurrentUser();////userdetails entity framework// findbyUserName
	
	public void fetchUserAndEnable(VerificationToken verificationToken);// findbyUserName
	
    String generateVerificationToken(User user);//randomUUID//
	
	public void verifyAccount(String token);//
	
	public AuthenticationResponse login(LoginRequest loginRequest);//builder// jwt provider
	
	public boolean isLoggedIn();//authentication
	
	
	

	
	
	
	
	
	
}
