package com.netcracker.parser.services.implementations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.netcracker.parser.entities.Role;
import com.netcracker.parser.entities.User;
import com.netcracker.parser.repositories.UserRepository;
import com.netcracker.parser.security.JwtResponse;
import com.netcracker.parser.security.Response;
import com.netcracker.parser.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.netcracker.parser.security.SecurityConstants.*;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public ResponseEntity<?> saveUser(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            return new ResponseEntity<>(
                    new Response("User with this username already exists"),
                    HttpStatus.BAD_REQUEST
            );

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Collections.singleton(Role.USER));
        userRepository.save(user);
        return ResponseEntity.ok(
               new Response("User has been registered")
        );
    }

    @Override
    public ResponseEntity<?> authenticateUser(User authenticatingUser) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticatingUser.getUsername(),
                            authenticatingUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            String token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(KEY.getBytes()));
            return ResponseEntity.ok(
                    new JwtResponse(
                            user.getUsername(),
                            token,
                            user.getAuthorities()
                    )
            );
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(
                    new Response("Invalid username or password"),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
