package com.otpapi.controller;


import com.otpapi.request.LoginRequestDto;
import com.otpapi.request.OtpRequestDto;
import com.otpapi.request.PasswordResetRequestDto;
import com.otpapi.request.SignupRequestDto;
import com.otpapi.service.AuthService;
import com.otpapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        return authService.signup(signupRequestDto);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequestDto otpRequestDto) {
        return authService.verifyOtp(otpRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequestDto passwordResetRequestDto) {
        return userService.resetPassword(passwordResetRequestDto);
    }

    @GetMapping("/welcome")
    public ResponseEntity<?> getWelcomePage(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
}
