package com.adamur.user_management.serviceImpl;

import com.adamur.user_management.dto.user.OTPVerificationResult;
import com.adamur.user_management.entity.User;
import com.adamur.user_management.repository.UserRepository;
import com.adamur.user_management.service.OTPService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class OTPServiceImpl implements OTPService {

    private UserRepository userRepository;

    @Autowired
    public OTPServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
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

    public OTPVerificationResult verifyUserOTPStatus(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // update user verification status
            user.setIsVerified(true);
            userRepository.save(user);

            return new OTPVerificationResult(true, "User status updated successfully");
        } else {
            // error in case the user is not found
            return new OTPVerificationResult(false, "User not found");
        }
    }
}