package com.Siddhesh.Airbnb.Controller;

import com.Siddhesh.Airbnb.DTOs.AuthResponseDTO;
import com.Siddhesh.Airbnb.DTOs.LoginRequestDTO;
import com.Siddhesh.Airbnb.Model.User;
import com.Siddhesh.Airbnb.Services.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public String currentUser(Authentication authentication) {
        return authentication.getName();
    }
}