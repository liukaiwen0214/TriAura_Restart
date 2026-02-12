package com.triaura.backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 注册请求：手机号/邮箱 + 验证码 + 密码
 */
@Data
public class RegisterRequest {

    /** PHONE/EMAIL */
    @NotBlank(message = "channel 不能为空")
    public String channel;

    /** 手机号或邮箱 */
    @NotBlank(message = "target 不能为空")
    public String target;

    /** 验证码 */
    @NotBlank(message = "code 不能为空")
    public String code;

    /** 密码（你可以后面增强复杂度规则） */
    @NotBlank(message = "password 不能为空")
    @Size(min = 6, max = 64, message = "password 长度需在 6~64")
    public String password;

    /** 显示名可选 */
    public String displayName;
    public String verificationId;

}