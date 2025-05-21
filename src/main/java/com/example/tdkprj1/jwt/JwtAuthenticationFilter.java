package com.example.tdkprj1.jwt;

import com.example.tdkprj1.auth.PrincipalDetails;
import com.example.tdkprj1.model.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
//UsernamePasswordAuthenticationFilter은 기본적으로 /login 경로로 들어오는 로그인 요청을 처리
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    //아이디/비밀번호로 Spring Security 인증 토큰 생성
    //내부 인증 매니저(AuthenticationManager)에게 검증 요청
    //여기서 DB에 있는 계정 정보와 비교해서 로그인 성공 여부 판단
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserDto userDto = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDto.getUserLoginid(), userDto.getUserPassword());
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //로그인 성공 시 → successfulAuthentication() 실행됨
    //인증된 사용자 정보를 꺼내서 (PrincipalDetails) JWT Access Token과 Refresh Token을 생성
    // 보안 설정된 HttpOnly 쿠키로 응답에 포함시킴
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        System.out.println("<UNK> <UNK> <UNK>: " + principal);
        String jwt = tokenProvider.createToken(principal.getUserDto().getId(), principal.getUserDto().getUserLoginid());
        String refresh = tokenProvider.createToken(
                principal.getUserDto().getId(), principal.getUserDto().getUserLoginid(), JwtProperties.REFRESH_EXPIRATION_TIME);

        Cookie jwtCookie = new Cookie("jwtToken", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(JwtProperties.EXPIRATION_TIME / 1000);

        Cookie refreshCookie = new Cookie("refreshToken", refresh);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge((int) (JwtProperties.REFRESH_EXPIRATION_TIME / 1000));

        response.addCookie(jwtCookie);
        response.addCookie(refreshCookie);
    }
}
