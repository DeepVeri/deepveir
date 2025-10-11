package com.deepecho.service.impl;

import com.deepecho.entity.Post;
import com.deepecho.mapper.PostMapper;
import com.deepecho.service.PostService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> getPosts(int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.findAll(offset, size);
    }

    @Override
    public List<Post> getUserPosts(String userId, int page, int size) {
        int offset = (page - 1) * size;
        return postMapper.findByUserId(userId, offset, size);
    }

    @Override
    public Post getPostById(String id) {
        return postMapper.findById(id);
    }

    @Override
    public int getTotalPosts() {
        return postMapper.countAll();
    }

    @Override
    public int getUserTotalPosts(String userId) {
        return postMapper.countByUserId(userId);
    }

    @Override
    public void incrementHotCount(String postId) {

    }
}