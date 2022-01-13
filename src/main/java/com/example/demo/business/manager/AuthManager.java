package com.example.demo.business.manager;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


import com.example.demo.business.service.AuthService;
import com.example.demo.business.service.MailService;
import com.example.demo.business.service.RefreshTokenService;
import com.example.demo.config.SecurityConfig;
import com.example.demo.exceptions.SpringRedditException;

import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.model.dto.AuthenticationResponse;
import com.example.demo.model.dto.LoginRequest;
import com.example.demo.model.dto.RefreshTokenRequest;
import com.example.demo.model.dto.RegisterRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;
import com.example.demo.security.JwtProvider;



@Service
@Transactional
public class AuthManager implements AuthService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepository;
	private VerificationTokenRepository verificationTokenRepository;
	private MailService mailService;
	private AuthenticationManager authenticationManager;
	private RefreshTokenService refreshTokenService;
	private JwtProvider jwtProvider;
	
	
	@Autowired
	public AuthManager(PasswordEncoder passwordEncoder,JwtProvider jwtProvider, UserRepository userRepository,
			VerificationTokenRepository verificationTokenRepository, MailService mailService,
			AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.verificationTokenRepository = verificationTokenRepository;
		this.mailService = mailService;
		this.authenticationManager = authenticationManager;
		this.refreshTokenService = refreshTokenService;
		this.jwtProvider= jwtProvider;
	}

	@Override
	public void signUp(RegisterRequest registerRequest) {
		
		User user= SecurityConfig.modelMapper().map(registerRequest,User.class);
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		
		userRepository.save(user);
		
		/*String token= generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate your Account",
				user.getEmail(),"Thank you for signing up to Spring Reddit, "+
		        "please click on the below url to activate your account " +
		        "http://localhost:8080/api/auth/accountVerification/" + token
				
				));*/
		
	}
	
	@Override
	public String generateVerificationToken(User user) {
		
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		
		verificationTokenRepository.save(verificationToken);
		
		return token;
	}

	@Override
	public User getCurrentUser() {
		
		org.springframework.security.core.userdetails.User principal= (org.springframework.security.core.userdetails.User)
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.getByUsername(principal.getUsername());
				
	}

	@Override
	public void fetchUserAndEnable(VerificationToken verificationToken) {
		
		String username= verificationToken.getUser().getUsername();
		User user= userRepository.getByUsername(username);
		user.setEnabled(true);
		userRepository.save(user);
		
	}

	@Override
	public void verifyAccount(String token) {

		Optional<VerificationToken> verificationToken= verificationTokenRepository.findBytoken(token);
		fetchUserAndEnable(verificationToken.orElseThrow(()-> new SpringRedditException("Invali Token")));
	}


	public AuthenticationResponse login(LoginRequest loginRequest) {
		try {
	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
	                loginRequest.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authenticate);
	        String token = jwtProvider.generateToken(authenticate);
	        return AuthenticationResponse.builder()
	                .authenticationToken(token)
	                .refreshToken(refreshTokenService.generateResfreshToken().getToken())
	                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
	                .username(loginRequest.getUsername())
	                .build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.getUsernameFromJwt(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

	@Override
	public boolean isLoggedIn() {
		
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		return ! (authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}

}
