package com.otpapi.service;

import com.otpapi.entity.User;
import com.otpapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setOtp(generateOTP());
        user.setVerified(false);
        return userRepository.save(user);
    }

    public boolean verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getOtp().equals(otp)) {
            user.setVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private String generateOTP() {
        return String.valueOf(new Random().nextInt(999999));
    }
}

