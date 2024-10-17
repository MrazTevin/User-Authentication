package com.adamur.user_management.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPVerificationResult {
    private boolean success;
    private String message;
}
