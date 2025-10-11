package com.deepecho.dto;

import com.deepecho.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long expireTime;
    private String username;
    private String email;
    private String nickname;
    private String avatar;
    private String role;
    private User user;
    private String id;
    private String phone;
    private String bio;
    private String location;
    private String website;
    private Integer gender;
    private String birthday;
    private Integer status;
    private String createdAt;
    private String updatedAt;
    private String lastLogin;
} 