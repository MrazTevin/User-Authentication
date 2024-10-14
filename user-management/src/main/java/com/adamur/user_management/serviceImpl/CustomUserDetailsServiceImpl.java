package com.adamur.user_management.serviceImpl;

import com.adamur.user_management.entity.User;
import com.adamur.user_management.repository.UserRepository;
import com.adamur.user_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

//    private UserService userService;

    @Autowired
    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .disabled(!user.getIsVerified())
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .authorities("ROLE_USER")
                .build();
    }
}