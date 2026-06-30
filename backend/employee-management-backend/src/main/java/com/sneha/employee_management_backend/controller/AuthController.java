package com.sneha.employee_management_backend.controller;

import com.sneha.employee_management_backend.dto.AuthResponse;
import com.sneha.employee_management_backend.dto.RegisterRequest;
import com.sneha.employee_management_backend.service.AuthService;
import com.sneha.employee_management_backend.dto.LoginRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request) {

        return authService.register(request);
    }
    @PostMapping("/login")
    public AuthResponse login(
        @Valid @RequestBody LoginRequest request) {

    return authService.login(request);
}
}