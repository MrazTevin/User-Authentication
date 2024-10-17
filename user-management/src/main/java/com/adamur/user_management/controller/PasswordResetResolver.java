package com.adamur.user_management.controller;

import com.adamur.user_management.dto.user.PasswordResetInput;
import com.adamur.user_management.dto.user.PasswordResetRequestInput;
import com.adamur.user_management.dto.user.PasswordResetResponse;
import com.adamur.user_management.exception.InvalidTokenException;
import com.adamur.user_management.exception.UserNotFoundException;
import com.adamur.user_management.service.PasswordResetService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


@Controller
public class PasswordResetResolver implements GraphQLMutationResolver {

    private final PasswordResetService passwordResetService;

    @Autowired
    public PasswordResetResolver(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @MutationMapping
    public PasswordResetResponse requestPasswordReset(@Argument PasswordResetRequestInput input) {
        try {
            passwordResetService.requestPasswordReset(input.getEmail());
            return new PasswordResetResponse(true, "Password reset email sent successfully");
        } catch (UserNotFoundException e) {
            return new PasswordResetResponse(false, "User not found with the provided email");
        } catch (Exception e) {
            return new PasswordResetResponse(false, "Failed to send password reset email: " + e.getMessage());
        }
    }

    @MutationMapping
    public PasswordResetResponse resetPassword(@Argument PasswordResetInput input) {
        try {
            passwordResetService.resetPassword(input.getToken(), input.getNewPassword());
            return new PasswordResetResponse(true, "Password reset successfully");
        } catch (InvalidTokenException e) {
            return new PasswordResetResponse(false, "Invalid or expired password reset token");
        } catch (UserNotFoundException e) {
            return new PasswordResetResponse(false, "User not found");
        } catch (Exception e) {
            return new PasswordResetResponse(false, "Failed to reset password: " + e.getMessage());
        }
    }
}