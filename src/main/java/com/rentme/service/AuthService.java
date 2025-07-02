package com.rentme.service;

import org.springframework.stereotype.Service;

import com.rentme.model.User;
import com.rentme.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user found with email: " + email);
        }
        return user;
    }
}
