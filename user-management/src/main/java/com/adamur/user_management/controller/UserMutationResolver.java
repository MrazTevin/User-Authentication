package com.adamur.user_management.controller;

import com.adamur.user_management.dto.user.UserInputDTO;
import com.adamur.user_management.dto.user.UserResponseDTO;
import com.adamur.user_management.service.EmailService;
import com.adamur.user_management.service.OTPService;
import com.adamur.user_management.service.UserService;
import com.adamur.user_management.util.OTPGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserMutationResolver {

    private UserService userService;
    private EmailService emailService;
    private OTPService otpService;

    @Autowired
    public UserMutationResolver(UserService userService, EmailService emailService, OTPService otpService) {
        this.userService = userService;
        this.emailService = emailService;
        this.otpService = otpService;
    }
    @MutationMapping
    public UserResponseDTO registerUser(@Argument(name="inputDTO") UserInputDTO inputDTO) {
        System.out.println("Attempting to register user with email: " + inputDTO.getEmail());

        try {
            UserResponseDTO response = userService.registerUser(inputDTO);
            System.out.println("User registered successfully. Response: " + response);
            response.setMessage("User registered successfully");

            String otp = OTPGenerator.generateOTP();
            otpService.storeOTP(inputDTO.getEmail(), otp);

            emailService.sendOTPEmail(inputDTO.getEmail(), otp);

            return response;
        } catch (Exception e) {
            System.out.println("Error occurred while registering user: " + e.getMessage());
            UserResponseDTO errorResponse = new UserResponseDTO();
            errorResponse.setMessage("Registration failed: " + e.getMessage());
            return errorResponse;
        }
    }

    @MutationMapping
    public Boolean verifyOTP(@Argument String email, @Argument String otp) {
        return otpService.verifyOTP(email, otp);
    }
}
