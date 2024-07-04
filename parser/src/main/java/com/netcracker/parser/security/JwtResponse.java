package com.netcracker.parser.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
@AllArgsConstructor
public class JwtResponse {

    private String username;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
}
