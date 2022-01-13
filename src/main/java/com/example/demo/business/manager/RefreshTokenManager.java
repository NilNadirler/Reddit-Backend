package com.example.demo.business.manager;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.business.service.RefreshTokenService;
import com.example.demo.exceptions.SpringRedditException;
import com.example.demo.model.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;

@Service
public class RefreshTokenManager implements RefreshTokenService {

	
	@Autowired
    RefreshTokenRepository refreshTokenRepository;
	
	
	@Override
	public RefreshToken generateResfreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
	}
	@Override
	public void deleteRefreshToken(String token) {
		
		refreshTokenRepository.deleteByToken(token);
		
	}

	@Override
	public void validateRefreshToken(String token) {
		refreshTokenRepository.findByToken(token)
		.orElseThrow(()-> new SpringRedditException("Invalid refresh Token"));
	}

	
}
