package com.otpapi.request;

import lombok.Data;

@Data
public class OtpRequestDto {
    private String email;
    private String otp;

}
