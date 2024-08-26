package com.otpapi.service;


import com.otpapi.entity.User;
import com.otpapi.repo.UserRepository;
import com.otpapi.request.PasswordResetRequestDto;
import com.otpapi.util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoderUtil passwordEncoderUtil;

    public ResponseEntity<?> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> resetPassword(PasswordResetRequestDto passwordResetRequestDto) {
        return ResponseEntity.ok("Password reset successful");
    }
}

