package com.triaura.backend.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 注册请求：手机号/邮箱 + 验证码 + 密码
 */
public class RegisterReq {

    /** PHONE/EMAIL */
    @NotBlank(message = "channel 不能为空")
    private String channel;

    /** 手机号或邮箱 */
    @NotBlank(message = "target 不能为空")
    private String target;

    /** 验证码 */
    @NotBlank(message = "code 不能为空")
    private String code;

    /** 密码（你可以后面增强复杂度规则） */
    @NotBlank(message = "password 不能为空")
    @Size(min = 6, max = 64, message = "password 长度需在 6~64")
    private String password;

    /** 显示名可选 */
    private String displayName;

    // getters/setters
    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}