package com.otpapi.controller;

import com.otpapi.request.SignupRequest;
import com.otpapi.service.UserService;
import com.otpapi.verifyrequst.VerifyOtpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        try {
            userService.registerUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok("OTP sent to your email");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API failure: " + e.getMessage());
        }
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            boolean isVerified = userService.verifyOtp(request.getEmail(), request.getOtp());
            if (isVerified) {
                return ResponseEntity.ok("User verified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
            }
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Invalid email address")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is incorrect");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API failure: " + e.getMessage());
        }
    }
}
