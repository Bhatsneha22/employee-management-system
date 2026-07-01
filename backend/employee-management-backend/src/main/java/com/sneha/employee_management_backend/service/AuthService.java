package com.sneha.employee_management_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sneha.employee_management_backend.dto.AuthResponse;
import com.sneha.employee_management_backend.dto.LoginRequest;
import com.sneha.employee_management_backend.dto.RegisterRequest;
import com.sneha.employee_management_backend.entity.Role;
import com.sneha.employee_management_backend.entity.User;
import com.sneha.employee_management_backend.repository.UserRepository;
import com.sneha.employee_management_backend.security.CustomUserDetailsService;
import com.sneha.employee_management_backend.security.JwtService;

@Service
public class AuthService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CustomUserDetailsService customUserDetailsService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    public AuthResponse register(RegisterRequest request) {

        logger.info("Registering user: {}", request.getUsername());

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {

            logger.warn("Username already exists: {}", request.getUsername());

            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            logger.warn("Email already exists: {}", request.getEmail());

            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Assign Role
        if (request.getRole() == null) {
            user.setRole(Role.USER);
        } else {
            user.setRole(request.getRole());
        }

        userRepository.save(user);

        logger.info("User {} registered successfully", request.getUsername());

        return new AuthResponse(
                null,
                "User registered successfully"
        );
    }

    public AuthResponse login(LoginRequest request) {

        logger.info("Authenticating user: {}", request.getUsername());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        logger.info("Authentication successful for {}", request.getUsername());

        String token = jwtService.generateToken(request.getUsername());

        logger.info("JWT generated successfully for {}", request.getUsername());

        return new AuthResponse(
                token,
                "Login Successful"
        );
    }
}