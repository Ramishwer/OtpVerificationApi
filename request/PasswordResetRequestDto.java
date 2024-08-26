package com.otpapi.request;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
    private String currentPassword;
    private String newPassword;

}
