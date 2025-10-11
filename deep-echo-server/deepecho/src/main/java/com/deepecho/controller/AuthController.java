package com.deepecho.controller;

import com.deepecho.dto.ApiResponse;
import com.deepecho.dto.LoginRequest;
import com.deepecho.dto.LoginResponse;
import com.deepecho.dto.RegisterRequest;
import com.deepecho.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest request) {
        logger.info("Received registration request for username: {}", request.getUsername());
        try {
            authService.register(request);
            logger.info("Registration successful for username: {}", request.getUsername());
            return ApiResponse.success("注册成功");
        } catch (Exception e) {
            logger.error("Registration failed for username: {}", request.getUsername(), e);
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        logger.info("Received login request for username: {}", request.getUsername());
        try {
            LoginResponse response = authService.login(request);
            logger.info("Login successful for username: {}", request.getUsername());
            return ApiResponse.success(response);
        } catch (Exception e) {
            logger.error("Login failed for username: {}", request.getUsername(), e);
            return ApiResponse.error(401, e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(Authentication authentication) {
        if (authentication != null) {
            logger.info("User {} logging out", authentication.getName());
            // Spring Security 会自动清除认证信息
            return ApiResponse.success("登出成功");
        }
        logger.warn("Unauthorized logout attempt");
        return ApiResponse.error(401, "未授权访问");
    }
} 