package com.example.ITdeviceMarket.service;

import com.example.ITdeviceMarket.model.User;
import com.example.ITdeviceMarket.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
            this.userRepository = userRepository;
            this.passwordEncoder = passwordEncoder;
        }

        public User registerUser(User user) {
            // Encrypt the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }

        public User findByEmail(String email) {
            return userRepository.findByEmail(email).orElse(null);
        }
}

