package com.deepecho.mapper;

import com.deepecho.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(Post post);

    List<Post> findAll(@Param("offset") int offset, @Param("limit") int limit);

    List<Post> findByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("limit") int limit);

    int countAll();

    int countByUserId(String userId);

    Post findById(String id);

    void updatePost(Post post);

    void deletePost(String id);
}
