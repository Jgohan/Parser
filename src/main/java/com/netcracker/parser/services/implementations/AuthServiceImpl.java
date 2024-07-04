package com.netcracker.parser.services.implementations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.netcracker.parser.entities.Role;
import com.netcracker.parser.entities.User;
import com.netcracker.parser.repositories.UserRepository;
import com.netcracker.parser.security.JwtResponse;
import com.netcracker.parser.security.Response;
import com.netcracker.parser.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;

import static com.netcracker.parser.security.SecurityConstants.EXPIRATION_TIME;
import static com.netcracker.parser.security.SecurityConstants.KEY;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;


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
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticatingUser.getUsername(),
                            authenticatingUser.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = (User) authentication.getPrincipal();
            var token = JWT.create()
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
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
