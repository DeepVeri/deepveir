package com.deepecho.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Post {
    private String id;
    private String userId;
    private String content;
    private String imageUrl;
    private Integer likeCount;
    private Integer commentCount;
    private Integer shareCount;
    private Integer hotCount;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 添加用户信息字段
    private String username;
    private String nickname;
    private String avatar;
}
