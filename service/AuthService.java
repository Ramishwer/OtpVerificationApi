package com.otpapi.service;


import com.otpapi.entity.User;
import com.otpapi.exception.InvalidFormDetailsException;
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

    public ResponseEntity<?> signup(SignupRequestDto signupRequestDto) {
//        if (!signupRequestDto.getPassword().equals(signupRequestDto.getConfirmPassword())) {
//            throw new InvalidFormDetailsException("Passwords do not match");
//        }

        User user = new User();
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(passwordEncoderUtil.encodePassword(signupRequestDto.getPassword()));
        userRepository.save(user);

        // Send OTP logic here

        return ResponseEntity.ok("Signup successful, please verify OTP");
    }

    public ResponseEntity<?> verifyOtp(OtpRequestDto otpRequestDto) {
        // OTP verification logic here
        // If OTP is incorrect, throw InvalidOtpException
        return ResponseEntity.ok("OTP verified successfully");
    }

    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null || !passwordEncoderUtil.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new InvalidFormDetailsException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
}
