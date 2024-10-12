package com.adamur.user_management.controller;

import com.adamur.user_management.dto.user.UserInputDTO;
import com.adamur.user_management.dto.user.UserResponseDTO;
import com.adamur.user_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserMutationResolver {

    private UserService userService;

    @Autowired
    public UserMutationResolver(UserService userService) {
        this.userService = userService;
    }
    @MutationMapping
    public UserResponseDTO registerUser(@Argument(name="inputDTO") UserInputDTO inputDTO) {
        System.out.println("Attempting to register user with email: " + inputDTO.getEmail());

        try {
            UserResponseDTO response = userService.registerUser(inputDTO);
            System.out.println("User registered successfully. Response: " + response);
            return response;
        } catch (Exception e) {
            System.out.println("Error occurred while registering user: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
