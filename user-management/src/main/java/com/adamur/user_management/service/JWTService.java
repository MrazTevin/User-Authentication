package com.adamur.user_management.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);

    String extractUsername(String token);

    Long getTokenExpiration(String token);
}
