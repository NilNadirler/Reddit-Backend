package com.example.demo.security;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import static io.jsonwebtoken.Jwts.parser;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.SpringRedditException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import lombok.Data;

@Service
@Data
public class JwtProvider {

	private KeyStore keyStore;
	
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;
	
	
	@PostConstruct
	public void init() throws java.io.IOException {
		try {
			 keyStore = KeyStore.getInstance("JKS");
	            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
	            keyStore.load(resourceAsStream, "secret".toCharArray());
		}
		catch(KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
			throw new SpringRedditException("Exception occured while loading keystore");
		}
	}
	
	
	public String generateToken(Authentication authentication) {
		
	    org.springframework.security.core.userdetails.User principal = 
	    		(org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.compact();
				
	}
	
	private PrivateKey getPrivateKey() {
		try {
			return(PrivateKey) keyStore.getKey("springblog","secret".toCharArray());
			
		}
		catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
			
			throw new SpringRedditException("Exception Occured from keystore ");
		}
	}
	public boolean validateToken(String jwt) {
		parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
		return true;
	}
	
	private PublicKey getPublickey() {
		 try {
			 return keyStore.getCertificate("springblog").getPublicKey();
		 } catch (KeyStoreException e) {
			 
			 throw new SpringRedditException("Exceptiom error");
		 }
	}
	
	public String getUsernameFromJwt(String token) {
		Claims claims=parser()
				.setSigningKey(getPublickey())
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
				
	}
	
}
