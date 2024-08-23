package com.otpapi.verifyrequst;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String otp;
    // Getters and setters
}
