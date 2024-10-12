package com.adamur.user_management.serviceImpl;

import com.adamur.user_management.dto.user.UserInputDTO;
import com.adamur.user_management.dto.user.UserResponseDTO;
import com.adamur.user_management.entity.User;
import com.adamur.user_management.exception.UserRegistrationException;
import com.adamur.user_management.repository.UserRepository;
import com.adamur.user_management.service.UserService;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO registerUser(UserInputDTO userInputDTO) {
        System.out.println("Starting user registration process for email: " + userInputDTO.getEmail());

        try {
            Optional<User> existingUser = userRepository.findByEmail(userInputDTO.getEmail());
            if (existingUser.isPresent()) {
                System.out.println("Attempt to register with existing email: " + userInputDTO.getEmail());
                throw new UserRegistrationException("Email is already in use");
            }

            User user = new User();
            user.setEmail(userInputDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userInputDTO.getPassword()));
            user.setIsVerified(false);

            System.out.println("Saving new user to database");
            User savedUser = userRepository.save(user);
            System.out.println("User saved successfully with ID: " + savedUser.getId());

            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setId(savedUser.getId());
            userResponseDTO.setEmail(savedUser.getEmail());
            userResponseDTO.setIsVerified(savedUser.getIsVerified());
            userResponseDTO.setCreatedAt(savedUser.getCreatedAt());

            System.out.println("User registration completed. Returning response: " + userResponseDTO);
            return userResponseDTO;
        } catch (Exception e) {
            System.out.println("Error in UserServiceImpl: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
