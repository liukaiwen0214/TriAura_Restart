package com.triaura.backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 发送验证码请求
 */
public class SendCodeReq {

    /** 渠道：PHONE/EMAIL */
    @NotBlank(message = "channel 不能为空")
    private String channel;

    /** 场景：REGISTER/LOGIN/RESET */
    @NotBlank(message = "scene 不能为空")
    private String scene;

    /** 手机号或邮箱 */
    @NotBlank(message = "target 不能为空")
    private String target;

    // getters/setters
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getScene() { return scene; }
    public void setScene(String scene) { this.scene = scene; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
}