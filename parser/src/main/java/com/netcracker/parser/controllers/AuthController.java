package com.netcracker.parser.controllers;

import com.netcracker.parser.entities.User;
import com.netcracker.parser.services.implementations.AuthServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }


    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        logger.info("Sign up: " + user.getUsername());

        authService.saveUser(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody User user) {
        logger.info("Sign in: " + user.getUsername());

        return authService.authenticateUser(user);
    }
}
