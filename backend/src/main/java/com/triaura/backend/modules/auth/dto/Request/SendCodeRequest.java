package com.triaura.backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发送验证码请求
 */
@Data
public class SendCodeRequest {

    /** 渠道：PHONE/EMAIL */
    @NotBlank(message = "channel 不能为空")
    public String channel;

    /** 场景：REGISTER/LOGIN/RESET */
    @NotBlank(message = "scene 不能为空")
    public String scene;

    /** 手机号或邮箱 */
    @NotBlank(message = "target 不能为空")
    public String target;
}