package com.adamur.user_management.controller;

import com.adamur.user_management.dto.user.LoginRequest;
import com.adamur.user_management.dto.user.LoginResponseDTO;
import com.adamur.user_management.service.JWTService;
import com.adamur.user_management.serviceImpl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
public class LoginResolver {

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;


    private CustomUserDetailsServiceImpl userDetailsService;

    @Autowired
    public LoginResolver(AuthenticationManager authenticationManager, JWTService jwtService, CustomUserDetailsServiceImpl userDetailsService) {
                this.authenticationManager = authenticationManager;
                this.jwtService = jwtService;
                this.userDetailsService = userDetailsService;
    }
    @MutationMapping
    public LoginResponseDTO login(@Argument LoginRequest input) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(input.getEmail());
            String token = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            Long remainingTime = jwtService.getTokenExpiration(token);
            return new LoginResponseDTO(token, refreshToken, remainingTime.intValue());
        }

        throw new RuntimeException("Invalid login credentials");
    }

    @MutationMapping
    public LoginResponseDTO refreshToken(@Argument String token) {
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (jwtService.validateToken(token, userDetails)) {
            String newToken = jwtService.generateToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);
            Long remainingTime = jwtService.getTokenExpiration(newToken);
            return new LoginResponseDTO(newToken, newRefreshToken,remainingTime.intValue());
        }

        throw new RuntimeException("Invalid refresh token");
    }
}