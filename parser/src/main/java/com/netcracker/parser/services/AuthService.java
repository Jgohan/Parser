package com.netcracker.parser.services;

import com.netcracker.parser.entities.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> saveUser(User user);

    ResponseEntity<?> authenticateUser(User user);

    User getAuthenticatedUser();
}
