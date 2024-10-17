package com.adamur.user_management.controller;

import com.adamur.user_management.dto.user.UserResponseDTO;
import com.adamur.user_management.entity.User;
import com.adamur.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class QueryResolver {

    private final UserRepository userRepository;

    @Autowired
    public QueryResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryMapping
    public UserResponseDTO getUserById(@Argument Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        User foundUser = user.get();

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(foundUser.getId());
        responseDTO.setEmail(foundUser.getEmail());
        responseDTO.setIsVerified(foundUser.getIsVerified());
        responseDTO.setCreatedAt(foundUser.getCreatedAt());

        return responseDTO;
    }
}
