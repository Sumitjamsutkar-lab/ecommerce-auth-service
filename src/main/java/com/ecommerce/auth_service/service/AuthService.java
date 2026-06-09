package com.ecommerce.auth_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.auth_service.dto.AuthResponse;
import com.ecommerce.auth_service.dto.LoginRequest;
import com.ecommerce.auth_service.dto.RegisterRequest;
import com.ecommerce.auth_service.entity.User;
import com.ecommerce.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service

public class AuthService {

	@Autowired
    private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
    public String register(RegisterRequest request) {

        if (userRepository.existsByUsername(
                request.getUsername())) {

            return "Username already exists";
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

    User user = userRepository
            .findByUsername(request.getUsername())
            .orElse(null);

    if (user == null) {
    	 throw new RuntimeException("User Not Found");
    }

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

    	throw new RuntimeException("Invalid Password");
    }

    String token =
            jwtService.generateToken(
                    user.getUsername());

    return new AuthResponse(token);
}
}