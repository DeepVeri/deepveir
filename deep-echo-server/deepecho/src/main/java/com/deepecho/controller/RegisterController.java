package com.deepecho.controller;

import com.deepecho.dto.ApiResponse;
import com.deepecho.mapper.UserMapper;
import com.deepecho.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@RestController
public class RegisterController {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final String[] AVATAR_COLORS = {
        "1abc9c", "2ecc71", "3498db", "9b59b6", "34495e",
        "16a085", "27ae60", "2980b9", "8e44ad", "2c3e50",
        "f1c40f", "e67e22", "e74c3c", "95a5a6", "f39c12",
        "d35400", "c0392b", "bdc3c7", "7f8c8d"
    };

    public RegisterController(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private String generateDefaultAvatar(String username) {
        int seed = username.hashCode();
        java.util.Random random = new java.util.Random(seed);
        String color = AVATAR_COLORS[random.nextInt(AVATAR_COLORS.length)];
        String initial = username.substring(0, 1).toUpperCase();
        return String.format("https://ui-avatars.com/api/?name=%s&background=%s&color=fff&size=200", initial, color);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody User user) {
        // Validate request parameters
        if (user == null || !StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getPassword()) || !StringUtils.hasText(user.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "用户名、密码和邮箱都是必填项"));
        }

        // Validate email format
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "邮箱格式不正确"));
        }

        // Validate password strength (at least 8 characters, containing letters and numbers)
        if (user.getPassword().length() < 8 || !user.getPassword().matches("(?=.*[0-9])(?=.*[a-zA-Z]).*")) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "密码至少需要8位，且必须包含字母和数字"));
        }

        // Check if username or email already exists
        if (userMapper.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "注册失败: 该用户名已被使用"));
        }

        if (userMapper.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "注册失败: 该邮箱已被注册"));
        }

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // 设置默认头像（如果未上传）
        if (!StringUtils.hasText(user.getAvatar())) {
            user.setAvatar(generateDefaultAvatar(user.getUsername()));
        }

        // Save the new user to database
        user.setId(UUID.randomUUID().toString());
        userMapper.insertUser(user);
        return ResponseEntity.ok(ApiResponse.success("注册成功"));
    }
}