package com.adamur.user_management.service;

import com.adamur.user_management.dto.user.OTPVerificationResult;
import com.adamur.user_management.dto.user.UserInputDTO;
import com.adamur.user_management.dto.user.UserResponseDTO;

public interface UserService {
    UserResponseDTO registerUser(UserInputDTO userInputDTO);

}


