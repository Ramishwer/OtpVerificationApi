package com.otpapi.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private final Map<String, String> otpStore = new HashMap<>();
    private final Map<String, Long> otpExpiry = new HashMap<>();

    private static final long OTP_VALIDITY_DURATION = TimeUnit.MINUTES.toMillis(5); // 5 minutes

    public String generateAndStoreOtp(String email) {
        String otp = generateOtp();
        otpStore.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + OTP_VALIDITY_DURATION);
        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        Long expiryTime = otpExpiry.get(email);

        if (storedOtp != null && storedOtp.equals(otp) && System.currentTimeMillis() < expiryTime) {
            otpStore.remove(email);
            otpExpiry.remove(email);
            return true;
        }
        return false;
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }
}

