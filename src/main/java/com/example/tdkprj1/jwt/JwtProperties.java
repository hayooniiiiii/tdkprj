package com.example.tdkprj1.jwt;

public interface JwtProperties {
    String SECRET = "TDK";
    int EXPIRATION_TIME=540000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    long REFRESH_EXPIRATION_TIME = 3000000000L;
}
