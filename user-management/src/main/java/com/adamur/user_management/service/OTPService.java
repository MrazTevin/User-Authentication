package com.adamur.user_management.service;

import com.adamur.user_management.dto.user.OTPVerificationResult;

public interface OTPService {
    void storeOTP(String email, String otp);
    String getOTP(String email);

    void clearOTP(String email);

    Boolean verifyOTP(String email, String otp);

    OTPVerificationResult verifyUserOTPStatus(String email);
}
