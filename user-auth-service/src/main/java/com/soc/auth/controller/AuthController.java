package com.soc.auth.controller;

import com.soc.auth.model.User;
import com.soc.auth.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // Check if the username is already taken
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username already exists!"));
        }
        // Save the new user to the database
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @Operation(summary = "Authenticate user and issue token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<User> userOpt = userRepository.findByUsername(username);
        
        // Verify username and password
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            // Generate a dummy OAuth2 Bearer token for Gateway testing
            String token = "oauth2-token-" + Base64.getEncoder().encodeToString((username + ":soc2026").getBytes());
            return ResponseEntity.ok(Map.of("access_token", token, "token_type", "Bearer", "username", username));
        }
        // Return 401 Unauthorized if login fails
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    }

    @Operation(summary = "Get all registered users")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        // Return a list of all users
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Operation(summary = "Delete a user by ID")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        // Check if the user exists before deleting
        if (!userRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
        // Delete the user from the database
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}