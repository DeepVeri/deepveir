package com.deepecho.controller;

import com.deepecho.dto.ApiResponse;
import com.deepecho.entity.Post;
import com.deepecho.service.PostService;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 获取动态列表（分页）
    @GetMapping
    public ApiResponse<Map<String, Object>> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Post> posts = postService.getPosts(page, size);
            int total = postService.getTotalPosts();
            
            Map<String, Object> result = new HashMap<>();
            result.put("posts", posts);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
            
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取动态列表失败：" + e.getMessage());
        }
    }

    // 获取指定用户的动态列表（分页）
    @GetMapping("/user/{userId}")
    public ApiResponse<Map<String, Object>> getUserPosts(
            @PathVariable String userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            List<Post> posts = postService.getUserPosts(userId, page, size);
            int total = postService.getUserTotalPosts(userId);
            
            Map<String, Object> result = new HashMap<>();
            result.put("posts", posts);
            result.put("total", total);
            result.put("page", page);
            result.put("size", size);
            
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取用户动态列表失败：" + e.getMessage());
        }
    }

    // 获取单条动态详情
    @GetMapping("/{id}")
    public ApiResponse<Post> getPostById(@PathVariable String id) {
        try {
            Post post = postService.getPostById(id);
            if (post == null) {
                return ApiResponse.error(404, "动态不存在");
            }
            return ApiResponse.success(post);
        } catch (Exception e) {
            return ApiResponse.error(500, "获取动态详情失败：" + e.getMessage());
        }
    }
}