package com.otpapi.request;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
}
