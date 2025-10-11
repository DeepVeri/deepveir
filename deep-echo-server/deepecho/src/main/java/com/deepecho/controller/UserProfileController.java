package com.deepecho.controller;

import com.deepecho.dto.AvatarCropDTO;
import com.deepecho.dto.UserProfileDTO;
import com.deepecho.security.RequiresAuthentication;
import com.deepecho.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @RequiresAuthentication
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UserProfileDTO profileDTO) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            userProfileService.updateProfile(username, profileDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "保存成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @RequiresAuthentication
    @PostMapping("/avatar")
    public ResponseEntity<?> updateAvatar(
        @RequestParam("avatar") MultipartFile avatar,
        @RequestParam(value = "x", required = false, defaultValue = "0") int x,
        @RequestParam(value = "y", required = false, defaultValue = "0") int y,
        @RequestParam(value = "width", required = false) Integer width,
        @RequestParam(value = "height", required = false) Integer height,
        @RequestParam(value = "rotate", required = false, defaultValue = "0") int rotate
    ) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            
            // 创建裁剪参数对象
            AvatarCropDTO cropDTO = new AvatarCropDTO();
            cropDTO.setX(x);
            cropDTO.setY(y);
            cropDTO.setRotate(rotate);
            
            // 如果没有提供宽度和高度，使用默认值
            if (width == null || height == null) {
                cropDTO.setWidth(800);  // 使用默认最大宽度
                cropDTO.setHeight(800); // 使用默认最大高度
            } else {
                cropDTO.setWidth(width);
                cropDTO.setHeight(height);
            }
            
            String avatarUrl = userProfileService.updateAvatar(username, avatar, cropDTO);
            
            // 修改返回格式
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "头像更新成功");
            response.put("data", new HashMap<String, String>() {{
                put("avatarUrl", avatarUrl);
            }});
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
} 