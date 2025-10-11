package com.deepecho.mapper;

import com.deepecho.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT COUNT(*) > 0 FROM users WHERE username = #{username}")
    boolean existsByUsername(String username);

    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean existsByEmail(String email);

    @Insert("INSERT INTO users (id, username, password, email, phone, nickname, avatar, gender, birthday, bio, location, website, status, created_at, updated_at, last_login, role) " +
            "VALUES (#{id}, #{username}, #{password}, #{email}, #{phone}, #{nickname}, #{avatar}, #{gender}, #{birthday}, #{bio}, #{location}, #{website}, #{status}, NOW(), NOW(), NOW(), #{role})")
    void insertUser(User user);

    @Update("UPDATE users SET password = #{password}, email = #{email}, phone = #{phone}, nickname = #{nickname}, " +
            "avatar = #{avatar}, gender = #{gender}, birthday = #{birthday}, bio = #{bio}, location = #{location}, " +
            "website = #{website}, status = #{status}, role = #{role}, updated_at = NOW() WHERE id = #{id}")
    void update(User user);

    @Update("UPDATE users SET last_login = NOW() WHERE id = #{id}")
    void updateLastLogin(String id);

    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    void updatePassword(User user);
} 