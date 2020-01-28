package com.netcracker.parser.security;

public interface SecurityConstants {
    long EXPIRATION_TIME = 60_000 * 30;
    String KEY = "CTRhTnySQrJL";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String AUTH_URL = "/auth/**";
}
