package com.sneha.employee_management_backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;

import com.sneha.employee_management_backend.dto.AuthResponse;
import com.sneha.employee_management_backend.dto.LoginRequest;
import com.sneha.employee_management_backend.dto.RegisterRequest;
import com.sneha.employee_management_backend.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
        logger.info("AuthController initialized");
    }

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request) {

        logger.info("Registration request received for user: {}",
                request.getUsername());

        AuthResponse response = authService.register(request);

        logger.info("User registered successfully: {}",
                request.getUsername());

        return response;
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        logger.info("Login request received for user: {}",
                request.getUsername());

        AuthResponse response = authService.login(request);

        logger.info("User logged in successfully: {}",
                request.getUsername());

        return response;
    }
}