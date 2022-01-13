package com.example.demo.business.service;

import com.example.demo.model.RefreshToken;

public interface RefreshTokenService {

	public RefreshToken generateResfreshToken();//UUID.random
	
	public void deleteRefreshToken(String token); 
	
	public void validateRefreshToken(String string);
}
