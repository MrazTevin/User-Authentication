package com.adamur.user_management.serviceImpl;

import com.adamur.user_management.dto.user.PasswordResetDTO;
import com.adamur.user_management.entity.PasswordResetToken;
import com.adamur.user_management.entity.User;
import com.adamur.user_management.exception.InvalidTokenException;
import com.adamur.user_management.exception.UserNotFoundException;
import com.adamur.user_management.repository.PasswordResetRespository;
import com.adamur.user_management.repository.UserRepository;
import com.adamur.user_management.service.EmailService;
import com.adamur.user_management.service.JWTService;
import com.adamur.user_management.service.PasswordResetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

    private final JWTService jwtService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final PasswordResetRespository passwordResetRespository;

    private final CustomUserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetServiceImpl.class);


    @Value("${jwt.reset.expiration}")
    private Long resetExpirationInMillis;

    // a concurrent map to store reset tokens in memory
    private final Map<String, PasswordResetDTO> resetTokens = new ConcurrentHashMap<>();

    @Autowired
    public PasswordResetServiceImpl(JWTService jwtService, EmailService emailService,
                                    UserRepository userRepository, PasswordEncoder passwordEncoder,
                                    CustomUserDetailsServiceImpl userDetailsService, PasswordResetRespository passwordResetRespository) {
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.passwordResetRespository = passwordResetRespository;
    }

    @Override
    public PasswordResetDTO requestPasswordReset(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());


        String token = jwtService.generatePasswordResetToken(userDetails);
        Date expiryDate = new Date(System.currentTimeMillis() + resetExpirationInMillis);

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user); // Ensure user is set correctly
        passwordResetToken.setExpiryDate(expiryDate);
        // Save the token to the database
        passwordResetRespository.save(passwordResetToken);

        // optionally persist in memory for faster access
        PasswordResetDTO passwordResetDTO = new PasswordResetDTO(token, user.getEmail(), expiryDate);
        resetTokens.put(token, passwordResetDTO);

        emailService.sendPasswordResetEmail(email, token);

        return passwordResetDTO;
    }

    @Override
    public void resetPassword(String token, String newPassword) throws InvalidTokenException {
        try {

            // Fetch token from the database

            PasswordResetToken passwordResetToken = passwordResetRespository.findByToken(token)
                    .orElseThrow(() -> new InvalidTokenException("Invalid password reset token"));

            //check if token is expired
            if (passwordResetToken.getExpiryDate().before(new Date())) {
                passwordResetRespository.delete(passwordResetToken);
                throw new InvalidTokenException("Expired password reset token");
            }

            User user = passwordResetToken.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            // remove token from memory
            resetTokens.remove(token);

            passwordResetRespository.delete(passwordResetToken); // remove token from database

        } catch (InvalidTokenException e) {
            logger.error("Invalid token error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while resetting password: {}", e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred while resetting the password", e);
        }
    }

}