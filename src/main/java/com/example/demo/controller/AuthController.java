package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.business.service.AuthService;
import com.example.demo.model.dto.AuthenticationResponse;
import com.example.demo.model.dto.LoginRequest;

import com.example.demo.model.dto.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(maxAge = 3600)
public class AuthController {

	 @Autowired
	 AuthService authService;
	 
	     @PostMapping("/signup")
		 public ResponseEntity<?> signup(@RequestBody RegisterRequest registerRequest){
			 
	    	 authService.signUp(registerRequest);
	    	 return ResponseEntity.ok("User Registration Successful");
		 }
	     
	     @GetMapping("accountVerification/{token}")
	      public ResponseEntity<?> verifyAccount(@PathVariable String token ){
	    	 authService.verifyAccount(token);
	    	 return ResponseEntity.ok("Account Activated Successfully");
	
	     }
	     
	     @PostMapping("/login")
	     public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) 
	     {
	    	 return ResponseEntity.ok(authService.login(loginRequest));
	     }
	     
	    /* @PostMapping("/refresh/token")
	     public AuthenticationResponse refreshTokens(@RequestBody RefreshTokenRequest refreshTokenRequest) {
	    	 
	    	 return authService.re
	     }*/
	     
	     
	    
}