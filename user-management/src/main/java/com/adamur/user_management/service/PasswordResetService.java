package com.adamur.user_management.service;

import com.adamur.user_management.dto.user.PasswordResetDTO;
import com.adamur.user_management.exception.InvalidTokenException;
import com.adamur.user_management.exception.UserNotFoundException;

public interface PasswordResetService {

    PasswordResetDTO requestPasswordReset(String email) throws UserNotFoundException;
    void resetPassword(String token, String newPassword) throws InvalidTokenException, UserNotFoundException;
}
