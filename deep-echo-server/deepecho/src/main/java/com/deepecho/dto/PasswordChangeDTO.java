package com.deepecho.dto;

import com.deepecho.validation.PasswordStrength;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class PasswordChangeDTO {
    @NotBlank(message = "当前密码不能为空")
    private String currentPassword;
    
    @NotBlank(message = "新密码不能为空")
    @PasswordStrength
    private String newPassword;
} 