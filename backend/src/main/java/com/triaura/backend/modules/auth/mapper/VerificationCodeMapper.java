package com.triaura.backend.modules.auth.mapper;

import com.triaura.backend.modules.auth.entity.VerificationCode;
import org.apache.ibatis.annotations.*;

@Mapper
public interface VerificationCodeMapper {

    @Insert("""
        INSERT INTO verification_code
        (id, channel, target, scene, code_hash, expires_at, used_at, send_count, created_at, updated_at)
        VALUES
        (#{id}, #{channel}, #{target}, #{scene}, #{codeHash}, #{expiresAt}, #{usedAt}, #{sendCount}, NOW(), NOW())
        """)
    int insert(VerificationCode vc);

    @Select("""
        SELECT * FROM verification_code
        WHERE channel=#{channel} AND target=#{target} AND scene=#{scene}
        ORDER BY created_at DESC
        LIMIT 1
        """)
    VerificationCode findLatest(String channel, String target, String scene);

    @Update("""
        UPDATE verification_code
        SET used_at = NOW()
        WHERE id = #{id} AND used_at IS NULL
        """)
    int markUsed(String id);
}