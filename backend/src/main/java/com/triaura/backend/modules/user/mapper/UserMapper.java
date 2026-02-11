package com.triaura.backend.modules.user.mapper;

import com.triaura.backend.modules.user.entity.AppUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM app_user WHERE email = #{email} LIMIT 1")
    AppUser findByEmail(String email);

    @Select("SELECT * FROM app_user WHERE phone = #{phone} LIMIT 1")
    AppUser findByPhone(String phone);

    @Select("SELECT * FROM app_user WHERE username = #{username} LIMIT 1")
    AppUser findByUsername(String username);

    @Insert("""
        INSERT INTO app_user
        (id, username, display_name, email, email_verified_at, phone, phone_verified_at,
         password_hash, role, status, created_at, updated_at)
        VALUES
        (#{id}, #{username}, #{displayName}, #{email}, #{emailVerifiedAt}, #{phone}, #{phoneVerifiedAt},
         #{passwordHash}, #{role}, #{status}, NOW(), NOW())
        """)
    int insert(AppUser user);
}