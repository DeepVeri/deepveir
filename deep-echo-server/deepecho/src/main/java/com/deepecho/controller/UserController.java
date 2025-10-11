package com.deepecho.controller;

import com.deepecho.dto.ApiResponse;
import com.deepecho.dto.PasswordChangeDTO;
import com.deepecho.dto.UserInfoResponse;
import com.deepecho.entity.User;
import com.deepecho.mapper.UserMapper;
import com.deepecho.security.RequiresAuthentication;
import com.deepecho.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @RequiresAuthentication
    @GetMapping("/info")
    public ApiResponse<UserInfoResponse> getUserInfo() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            logger.info("Current username: {}", username);
            
            User user = userMapper.findByUsername(username);
            logger.info("Found user: {}", user);
            
            if (user == null) {
                logger.error("User not found for username: {}", username);
                return ApiResponse.error(401, "未授权访问");
            }

            // 更新最后登录时间
            userMapper.updateLastLogin(user.getId());

            UserInfoResponse userInfo = UserInfoResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .avatar(user.getAvatar())
                    .bio(user.getBio())
                    .role(user.getRole())
                    .createdAt(user.getCreatedAt())
                    .lastLogin(user.getLastLogin())
                    .phone(user.getPhone())
                    .build();

            return ApiResponse.success(userInfo);
        } catch (Exception e) {
            logger.error("Error getting user info", e);
            return ApiResponse.error(401, "未授权访问");
        }
    }

    @RequiresAuthentication
    @PutMapping("/password")
    public ApiResponse<?> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.changePassword(username, passwordChangeDTO.getCurrentPassword(), passwordChangeDTO.getNewPassword());
            return ApiResponse.success("密码修改成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @RequiresAuthentication
    @PutMapping("/email")
    public ApiResponse<?> changeEmail(@RequestBody Map<String, String> request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String newEmail = request.get("email");
            logger.info("Received email change request for username: {}", username);
            logger.info("New email: {}", newEmail);
            userService.changeEmail(username, newEmail);
            return ApiResponse.success("邮箱修改成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }

    @RequiresAuthentication
    @PutMapping("/phone")
    public ApiResponse<?> bindPhone(@RequestBody Map<String, String> request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String newPhone = request.get("phone");
            userService.bindPhone(username, newPhone);
            return ApiResponse.success("手机号绑定成功");
        } catch (Exception e) {
            return ApiResponse.error(400, e.getMessage());
        }
    }
} 