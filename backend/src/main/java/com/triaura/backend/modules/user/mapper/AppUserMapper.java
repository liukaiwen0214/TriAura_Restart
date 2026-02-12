package com.triaura.backend.modules.user.mapper;

import com.triaura.backend.modules.user.entity.AppUser;
import org.apache.ibatis.annotations.*;

public interface AppUserMapper {

    @Select("select * from app_user where email = #{email} limit 1")
    AppUser findByEmail(@Param("email") String email);

    @Select("select * from app_user where phone = #{phone} limit 1")
    AppUser findByPhone(@Param("phone") String phone);

    @Select("select * from app_user where id = #{id} limit 1")
    AppUser findById(@Param("id") String id);

    @Insert("""
        insert into app_user(
          id, username, display_name,
          email, email_verified_at,
          phone, phone_verified_at,
          password_hash,
          role, status,
          last_login_at
        ) values (
          #{id}, #{username}, #{displayName},
          #{email}, #{emailVerifiedAt},
          #{phone}, #{phoneVerifiedAt},
          #{passwordHash},
          #{role}, #{status},
          #{lastLoginAt}
        )
    """)
    int insert(AppUser u);

    @Update("""
        update app_user
        set last_login_at = now()
        where id = #{id}
    """)
    int touchLastLogin(@Param("id") String id);
}