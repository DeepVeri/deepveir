package com.deepecho.service;

import com.deepecho.dto.LoginRequest;
import com.deepecho.dto.LoginResponse;
import com.deepecho.entity.User;
import com.deepecho.mapper.UserMapper;
import com.deepecho.utils.OssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.Arrays;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final OssUtil ossUtil;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, OssUtil ossUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.ossUtil = ossUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Loading user by username: {}", username);
        
        User user = userMapper.findByUsername(username);
        if (user == null) {
            logger.warn("User not found with username: {}", username);
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        logger.debug("Found user: {}, role: {}", username, user.getRole());

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole() != null ? user.getRole() : "ROLE_USER"))
        );
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userMapper.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return LoginResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .token("JWT_TOKEN") // 这里应该生成真实的JWT token
                .build();
    }

    @Override
    public void changePassword(String username, String currentPassword, String newPassword) {
        User user = userMapper.findByUsername(username);
        if (user == null || !passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BadCredentialsException("当前密码不正确");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updatePassword(user);
    }

    @Override
    public void changeEmail(String username, String newEmail) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        user.setEmail(newEmail);
        userMapper.update(user);
    }

    @Override
    public void bindPhone(String username, String newPhone) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }
        user.setPhone(newPhone);
        userMapper.update(user);
    }

    @Override
    public void register(User user) {
        // 设置默认头像
        user.setAvatar("default_avatar.jpg");
        
        // 保存用户信息
        userMapper.insertUser(user);
    }

    @Override
    public String updateAvatar(String username, MultipartFile avatar) throws IOException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("用户不存在");
        }

        // 验证文件类型
        String contentType = avatar.getContentType();
        if (!Arrays.asList("image/jpeg", "image/png", "image/gif").contains(contentType)) {
            throw new IllegalArgumentException("只支持 JPG、PNG 和 GIF 格式的图片");
        }

        // 生成唯一的文件名
        String newFilename = UUID.randomUUID().toString() + ".png";
        
        // 确保上传目录存在
        Path uploadPath = Paths.get("uploads/avatars");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 保存文件
        Path filePath = uploadPath.resolve(newFilename);
        Files.write(filePath, avatar.getBytes());

        // 删除旧头像
        if (user.getAvatar() != null) {
            try {
                String oldFilename = user.getAvatar().substring(user.getAvatar().lastIndexOf("/") + 1);
                Path oldFilePath = uploadPath.resolve(oldFilename);
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                logger.warn("Failed to delete old avatar", e);
            }
        }

        // 更新用户头像URL
        String avatarUrl = "/avatars/" + newFilename;
        user.setAvatar(avatarUrl);
        userMapper.update(user);

        return avatarUrl;
    }
} 