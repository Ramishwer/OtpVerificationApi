package com.otpapi.service;

import com.otpapi.entity.User;
import com.otpapi.exception.InvalidFormDetailsException;
import com.otpapi.exception.InvalidOtpException;
import com.otpapi.repo.UserRepository;
import com.otpapi.request.LoginRequestDto;
import com.otpapi.request.OtpRequestDto;
import com.otpapi.request.SignupRequestDto;
import com.otpapi.security.JwtUtil;
import com.otpapi.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    public ResponseEntity<?> signup(SignupRequestDto signupRequestDto) {
        // Validate passwords
//        if (!signupRequestDto.getPassword().equals(signupRequestDto.getConfirmPassword())) {
//            throw new InvalidFormDetailsException("Passwords do not match");
//        }


        User user = new User();
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(passwordEncoderUtil.encodePassword(signupRequestDto.getPassword()));
        userRepository.save(user);


        String otp = otpService.generateAndStoreOtp(user.getEmail());
        emailService.sendOtp(user.getEmail(), otp);

        return ResponseEntity.ok("Signup successful, please verify OTP");
    }

    public ResponseEntity<?> verifyOtp(OtpRequestDto otpRequestDto) {
        if (otpService.validateOtp(otpRequestDto.getEmail(), otpRequestDto.getOtp())) {

            String token = generateTokenForUser(otpRequestDto.getEmail());
            return ResponseEntity.ok("OTP verified successfully. Token: " + token);
        } else {
            throw new InvalidOtpException("Invalid OTP");
        }
    }

    private String generateTokenForUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new InvalidFormDetailsException("User not found");
        }

        // Generate and return JWT token
        return jwtUtil.generateToken(user.getEmail());
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null || !passwordEncoderUtil.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new InvalidFormDetailsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return token;
    }
}
