package com.humandigitaltwin.backend.service;

import com.humandigitaltwin.backend.entity.User;
import com.humandigitaltwin.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User updateUser(
            Long userId,
            User updatedUser) {

        Optional<User> existingUser =
                userRepository.findById(userId);

        if (existingUser.isEmpty()) {
            return null;
        }

        User user = existingUser.get();

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }

    public User loginUser(
            String email,
            String password) {

        List<User> users =
                userRepository.findAll();

        for (User user : users) {

            if (
                user.getEmail().equals(email) &&
                passwordEncoder.matches(
                    password,
                    user.getPassword()
                )
            ) {
                return user;
            }
        }

        return null;
    }
}