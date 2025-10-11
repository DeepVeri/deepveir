package com.deepecho.service;

import com.deepecho.dto.AvatarCropDTO;
import com.deepecho.dto.PasswordChangeDTO;
import com.deepecho.dto.UserProfileDTO;
import com.deepecho.entity.User;
import com.deepecho.repository.UserRepository;
import com.deepecho.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OssUtil ossUtil;

    @Value("${avatar.max.width:800}")
    private int maxAvatarWidth;

    @Value("${avatar.max.height:800}")
    private int maxAvatarHeight;

    public void updateProfile(String username, UserProfileDTO profileDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 验证用户名是否匹配
        if (!user.getUsername().equals(profileDTO.getUsername())) {
            throw new RuntimeException("用户名不能修改");
        }

        // 更新用户信息
        user.setEmail(profileDTO.getEmail());
        user.setBio(profileDTO.getBio());
        userRepository.save(user);
    }

    public String updateAvatar(String username, MultipartFile avatar, AvatarCropDTO cropDTO) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 验证文件类型
        String contentType = avatar.getContentType();
        if (!isValidImageType(contentType)) {
            throw new RuntimeException("只支持 JPG、PNG 和 GIF 格式的图片");
        }

        // 使用Thumbnailator处理图片
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(avatar.getInputStream())
                .sourceRegion(cropDTO.getX(), cropDTO.getY(), cropDTO.getWidth(), cropDTO.getHeight())
                .size(maxAvatarWidth, maxAvatarHeight)
                .rotate(cropDTO.getRotate())
                .outputFormat("png")
                .toOutputStream(outputStream);

        // 创建ByteArrayInputStream并上传到OSS
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        String fileName = "avatars/" + UUID.randomUUID().toString() + ".png";
        String avatarUrl = ossUtil.uploadFile(inputStream, fileName, "image/png");

        // 更新用户头像URL
        user.setAvatar(avatarUrl);
        userRepository.save(user);

        return avatarUrl;
    }

    private boolean isValidImageType(String contentType) {
        return contentType != null && (
            contentType.equals("image/jpeg") ||
            contentType.equals("image/png") ||
            contentType.equals("image/gif")
        );
    }

    public void changePassword(String username, PasswordChangeDTO passwordDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 验证当前密码
        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("当前密码错误");
        }

        // 验证新密码不能与当前密码相同
        if (passwordDTO.getCurrentPassword().equals(passwordDTO.getNewPassword())) {
            throw new RuntimeException("新密码不能与当前密码相同");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        userRepository.save(user);
    }
} 