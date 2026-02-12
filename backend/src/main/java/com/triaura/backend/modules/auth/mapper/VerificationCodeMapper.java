package com.triaura.backend.modules.auth.mapper;

import com.triaura.backend.modules.auth.entity.VerificationCode;
import org.apache.ibatis.annotations.*;

public interface VerificationCodeMapper {

    /**
     * 插入一条验证码记录（send_count 默认 1）
     */
    @Insert("""
        insert into verification_code(
          id, channel, target, scene,
          code_hash, expires_at, used_at, send_count
        ) values (
          #{id}, #{channel}, #{target}, #{scene},
          #{codeHash}, #{expiresAt}, #{usedAt}, #{sendCount}
        )
    """)
    int insert(VerificationCode v);

    /**
     * 查询最近一条验证码（按 channel + target + scene）
     */
    @Select("""
        select * from verification_code
        where channel = #{channel}
          and scene = #{scene}
          and target = #{target}
        order by created_at desc
        limit 1
    """)
    VerificationCode findLatest(@Param("channel") String channel,
                                @Param("scene") String scene,
                                @Param("target") String target);

    /**
     * 标记验证码已使用（used_at 写入当前时间）
     */
    @Update("""
        update verification_code
        set used_at = now(), updated_at = now()
        where id = #{id}
    """)
    int markUsed(@Param("id") String id);

    @Select("""
    select * from verification_code
    where id = #{id}
    limit 1
""")
    VerificationCode findById(@Param("id") String id);

    /**
     * 原子消费：只有未使用时才更新成功
     */
    @Update("""
    update verification_code
    set used_at = now(), updated_at = now()
    where id = #{id}
      and used_at is null
""")
    int markUsedIfNotUsed(@Param("id") String id);
}