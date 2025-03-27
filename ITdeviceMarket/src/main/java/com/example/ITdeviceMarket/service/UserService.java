package com.example.ITdeviceMarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.repository.UserRepository;
import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        userRepository.save(user);
    }


    // Load user for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(email);
        return userBuilder
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    // Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}


