package com.hospital.controller;

import com.hospital.model.User;
import com.hospital.service.AuthService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .role(User.Role.valueOf(request.getRole()))
                .build();
        User saved = authService.register(user);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "User registered successfully",
                "username", saved.getUsername()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "username", request.getUsername()
        ));
    }

    @Data
    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private String role;
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
