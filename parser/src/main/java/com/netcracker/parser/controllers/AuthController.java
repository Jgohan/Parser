package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.User;
import com.netcracker.parser.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthService authService;


    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        log.info("Sign up: {}", user.getUsername());

        return authService.saveUser(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        log.info("Sign in: {}", user.getUsername());

        return authService.authenticateUser(user);
    }
}
