package com.triaura.backend.modules.user.mapper;

import com.triaura.backend.modules.auth.entity.AppUserSession;
import org.apache.ibatis.annotations.*;

public interface AppUserSessionMapper {

    @Insert("""
        insert into app_user_session(id, user_id, token, expires_at, revoked_at)
        values(#{id}, #{userId}, #{token}, #{expiresAt}, #{revokedAt})
    """)
    int insert(AppUserSession s);

    @Select("select * from app_user_session where token = #{token} limit 1")
    AppUserSession findByToken(@Param("token") String token);

    @Update("""
        update app_user_session
        set revoked_at = now(), updated_at = now()
        where token = #{token} and revoked_at is null
    """)
    int revokeByToken(@Param("token") String token);
}