package com.adamur.user_management.serviceImpl;

import com.adamur.user_management.service.OTPService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class OTPServiceImpl implements OTPService {
    private final Map<String, OTPData> otpStore = new ConcurrentHashMap<>();
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes

    @Override
    public void storeOTP(String email, String otp) {
        otpStore.put(email, new OTPData(otp, System.currentTimeMillis()));
    }

    @Override
    public String getOTP(String email) {
        OTPData otpData = otpStore.get(email);
        if (otpData != null && !isOTPExpired(otpData)) {
            return otpData.otp();
        }
        return null;
    }

    @Override
    public void clearOTP(String email) {
        otpStore.remove(email);
    }

    private boolean isOTPExpired(OTPData otpData) {
        return System.currentTimeMillis() > otpData.timestamp() + OTP_VALID_DURATION;
    }


        private record OTPData(String otp, long timestamp) {
        }

    public Boolean verifyOTP(String email, String otp) {
        String storedOTP = getOTP(email);
        if (storedOTP != null && storedOTP.equals(otp)) {
            clearOTP(email);
            return true;
        }
        return false;
    }
}