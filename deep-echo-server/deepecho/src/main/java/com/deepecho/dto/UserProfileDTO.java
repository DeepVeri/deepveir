package com.deepecho.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
public class UserProfileDTO {
    private String username;
    
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Size(max = 500, message = "个人简介不能超过500字")
    private String bio;
} 