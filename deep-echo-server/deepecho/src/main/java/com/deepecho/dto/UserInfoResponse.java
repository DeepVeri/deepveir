package com.deepecho.dto;

import lombok.Data;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@Builder
public class UserInfoResponse {
    private String id;
    private String username;
    private String email;
    private String avatar;
    private String bio;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String phone;
} 