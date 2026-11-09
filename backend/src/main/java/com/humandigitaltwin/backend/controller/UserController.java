package com.humandigitaltwin.backend.controller;

import com.humandigitaltwin.backend.entity.User;
import com.humandigitaltwin.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(
            @PathVariable Long userId) {

        User user = userService.getUserById(userId)
                .orElse(null);

        if (user == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of(
                            "message",
                            "User not found."
                    ));
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long userId,
            @RequestBody User updatedUser) {

        User user =
                userService.updateUser(
                        userId,
                        updatedUser
                );

        if (user == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of(
                            "message",
                            "User not found."
                    ));
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody User user) {

        User loggedInUser =
                userService.loginUser(
                        user.getEmail(),
                        user.getPassword()
                );

        if (loggedInUser == null) {
            return ResponseEntity
                    .status(401)
                    .body(Map.of(
                            "message",
                            "Invalid email or password."
                    ));
        }

        return ResponseEntity.ok(loggedInUser);
    }
}