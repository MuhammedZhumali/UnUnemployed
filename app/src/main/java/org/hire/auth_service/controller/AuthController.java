package org.hire.auth_service.controller;

import org.springframework.web.bind.annotation.*;
import org.hire.auth_service.service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public void register(@RequestParam Long userId, @RequestParam String password){
        authService.registerUser(userId, password);
    }

    @PostMapping("/login")
    public void login(@RequestParam Long userId, @RequestParam String password){
        authService.login(userId, password);
    }
}
