package com.adamur.user_management.service;

public interface OTPService {
    void storeOTP(String email, String otp);
    String getOTP(String email);

    void clearOTP(String email);

    Boolean verifyOTP(String email, String otp);
}
