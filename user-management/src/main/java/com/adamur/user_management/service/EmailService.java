package com.adamur.user_management.service;

public interface EmailService {
    void sendOTPEmail(String to, String otp);
}
