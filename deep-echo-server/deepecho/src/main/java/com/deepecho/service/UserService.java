package com.deepecho.service;

import com.deepecho.dto.LoginRequest;
import com.deepecho.dto.LoginResponse;
import com.deepecho.entity.User;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface UserService {
    LoginResponse login(LoginRequest loginRequest);
    void changePassword(String username, String currentPassword, String newPassword);
    void changeEmail(String username, String newEmail);
    void bindPhone(String username, String newPhone);
    void register(User user);
    String updateAvatar(String username, MultipartFile avatar) throws IOException;
} 