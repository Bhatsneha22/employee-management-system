package com.sneha.employee_management_backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sneha.employee_management_backend.dto.AuthResponse;
import com.sneha.employee_management_backend.dto.LoginRequest;
import com.sneha.employee_management_backend.dto.RegisterRequest;
import com.sneha.employee_management_backend.entity.User;
import com.sneha.employee_management_backend.repository.UserRepository;
import com.sneha.employee_management_backend.security.CustomUserDetailsService;
import com.sneha.employee_management_backend.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;


   public AuthService(UserRepository userRepository,
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
public AuthResponse login(LoginRequest request) {

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );

    String token = jwtService.generateToken(request.getUsername());

    return new AuthResponse(token, "Login Successful");
}
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default role
        user.setRole("USER");

        userRepository.save(user);

        return new AuthResponse(null, "User registered successfully");
    }

}