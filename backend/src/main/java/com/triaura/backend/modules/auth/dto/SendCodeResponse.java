package com.triaura.backend.modules.auth.dto;

import lombok.Data;

@Data
public class SendCodeResponse {
    public String verificationId;

    public static SendCodeResponse of(String id) {
        SendCodeResponse r = new SendCodeResponse();
        r.verificationId = id;
        return r;
    }
}