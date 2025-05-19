package com.example.tdkprj1.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    //jwt properties에서 상수 가지고 오기
    private final String SECRET_KEY=JwtProperties.SECRET;
    private final int EXPIRATION_TIME=JwtProperties.EXPIRATION_TIME;
    private final long REFRESH_EXPIRATION_TIME=JwtProperties.REFRESH_EXPIRATION_TIME;


    //JWT 생성 메소드
    public String createToken(long userId, String userLoginid){
        return JWT.create()
                .withSubject("계출서토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("userId",userId)
                .withClaim("userLoginid",userLoginid)
                .sign(Algorithm.HMAC512(SECRET_KEY));

    }

    // JWT 생성 메서드 (만료 시간이 다를 경우) -> refresh token
    public String createToken(long userId, String userLoginid , long expirationTime) {
        return JWT.create()
                .withSubject("캘린다이어리토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withClaim("userId", userId)
                .withClaim("userLoginid", userLoginid)
                .sign(Algorithm.HMAC512(SECRET_KEY)); // HMAC512 알고리즘 사용
    }

    //jwt 검증 메소드
    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .build()
                    .verify(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    //jwt claim 추출 메소드
    public DecodedJWT getClaims(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .build()
                .verify(token);
    }

    //jwt에서 사용자 id 추출메소드
    public int getUserId(String token) {
        return getClaims(token).getClaim("userId").asInt();
    }



}
