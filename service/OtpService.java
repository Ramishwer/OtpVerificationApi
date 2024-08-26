package com.otpapi.service;

import com.otpapi.entity.OtpVerification;
import com.otpapi.repo.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    public OtpVerification saveOtp(OtpVerification otpVerification) {
        return otpRepository.save(otpVerification);
    }

    public Optional<OtpVerification> findByEmail(String email) {
        return otpRepository.findByEmail(email);
    }

    public void deleteOtp(OtpVerification otpVerification) {
        otpRepository.delete(otpVerification);
    }
}
