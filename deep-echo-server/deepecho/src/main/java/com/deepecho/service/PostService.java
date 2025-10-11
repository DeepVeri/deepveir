package com.deepecho.service;

import com.deepecho.entity.Post;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public interface PostService {
    List<Post> getPosts(int page, int size);
    List<Post> getUserPosts(String userId, int page, int size);
    Post getPostById(String id);
    int getTotalPosts();
    int getUserTotalPosts(String userId);
    void incrementHotCount(String postId);
}