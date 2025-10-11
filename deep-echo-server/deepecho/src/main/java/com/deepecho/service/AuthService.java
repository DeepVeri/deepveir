package com.deepecho.service;

import com.deepecho.dto.LoginRequest;
import com.deepecho.dto.LoginResponse;
import com.deepecho.dto.RegisterRequest;
import com.deepecho.entity.User;
import com.deepecho.mapper.UserMapper;
import com.deepecho.security.JwtTokenProvider;
import com.deepecho.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.Random;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final String[] AVATAR_COLORS = {
        "1abc9c", "2ecc71", "3498db", "9b59b6", "34495e",
        "16a085", "27ae60", "2980b9", "8e44ad", "2c3e50",
        "f1c40f", "e67e22", "e74c3c", "95a5a6", "f39c12",
        "d35400", "c0392b", "bdc3c7", "7f8c8d"
    };
    private static final Random random = new Random();

    @Value("${app.jwt.expireTime}")
    private Long jwtExpirationInMs;

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final JwtUtil jwtUtil;  // 添加 JwtUtil

    public AuthService(UserMapper userMapper, 
                      PasswordEncoder passwordEncoder,
                      AuthenticationManager authenticationManager,
                      JwtTokenProvider tokenProvider,
                      JwtUtil jwtUtil) {  // 添加 JwtUtil 参数
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.jwtUtil = jwtUtil;
    }

    private String generateDefaultAvatar(String username) {
        // 使用用户名生成一个固定的随机数
        int seed = username.hashCode();
        random.setSeed(seed);
        
        // 随机选择一个颜色
        String color = AVATAR_COLORS[random.nextInt(AVATAR_COLORS.length)];
        
        // 获取用户名首字母
        String initial = username.substring(0, 1).toUpperCase();
        
        // 使用UI Avatars服务生成头像
        return String.format("https://ui-avatars.com/api/?name=%s&background=%s&color=fff&size=200", 
            initial, color);
    }

    public void register(RegisterRequest request) {
        logger.info("Processing registration for username: {}", request.getUsername());
        
        // 检查用户名是否已存在
        if (userMapper.existsByUsername(request.getUsername())) {
            logger.warn("Registration failed: Username {} already exists", request.getUsername());
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (request.getEmail() != null && !request.getEmail().isEmpty() && userMapper.existsByEmail(request.getEmail())) {
            logger.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new RuntimeException("邮箱已被注册");
        }

        // 创建新用户
        User user = new User();
        user.setId(UUID.randomUUID().toString());  // 生成 UUID
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setNickname(request.getNickname());
        // 生成默认头像
        user.setAvatar(generateDefaultAvatar(request.getUsername()));
        user.setGender(request.getGender());
        user.setBirthday(request.getBirthday());
        user.setBio(request.getBio());
        user.setLocation(request.getLocation());
        user.setWebsite(request.getWebsite());
        user.setStatus(1); // 1: 正常
        user.setRole("ROLE_USER"); // 设置默认角色

        userMapper.insertUser(user);
        logger.info("User registered successfully: {}", request.getUsername());
    }

    public LoginResponse login(LoginRequest request) {
        logger.info("Processing login for username: {}", request.getUsername());
        
        try {
            // 验证用户名和密码
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            // 设置认证信息
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 生成 JWT token
            String token = tokenProvider.generateToken(authentication);
            
            // 生成绝对过期时间戳
            long expireTime = System.currentTimeMillis() + jwtExpirationInMs; // 绝对时间戳（毫秒）

            // 获取用户信息
            User user = userMapper.findByUsername(request.getUsername());
            if (user == null) {
                logger.error("User not found after successful authentication: {}", request.getUsername());
                throw new RuntimeException("用户不存在");
            }

            // 更新最后登录时间
            userMapper.updateLastLogin(user.getId());

            // 构建响应
            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .expireTime(expireTime)  // 这里是绝对时间戳
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .avatar(user.getAvatar())
                    .role(user.getRole())
                    .id(user.getId())
                    .phone(user.getPhone())
                    .bio(user.getBio())
                    .location(user.getLocation())
                    .website(user.getWebsite())
                    .gender(user.getGender())
                    .birthday(user.getBirthday() != null ? user.getBirthday().toString() : null)
                    .status(user.getStatus())
                    .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                    .updatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null)
                    .lastLogin(user.getLastLogin() != null ? user.getLastLogin().toString() : null)
                    .build();
            
            logger.info("Login successful for user: {}", request.getUsername());
            return response;
            
        } catch (Exception e) {
            logger.error("Login failed for user: {}, error: {}", request.getUsername(), e.getMessage(), e);
            throw new RuntimeException("用户名或密码错误");
        }
    }
} 