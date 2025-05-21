package com.example.tdkprj1.config;

import com.example.tdkprj1.jwt.JwtAuthenticationFilter;
import com.example.tdkprj1.jwt.JwtAuthorizationFilter;
import com.example.tdkprj1.jwt.JwtTokenProvider;
import com.example.tdkprj1.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authConfig;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authConfig.getAuthenticationManager();

        // CORS 필터
        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // CSRF 설정
                .csrf(csrf -> csrf.disable())
                //세션을 생성하지 않도록 설정함
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT Authentication Filter (폼 로그인 처리)
        JwtAuthenticationFilter authFilter = new JwtAuthenticationFilter(authManager, tokenProvider);
        authFilter.setFilterProcessesUrl("/login/user");
        http.addFilter(authFilter);

        // JWT Authorization Filter (모든 요청 인가)
        http.addFilterAfter(
                new JwtAuthorizationFilter(authManager, userRepository, tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );

        // 권한 설정 (Spring Security 6.1+)
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login/**", "/account/join","/input").permitAll()
                .anyRequest().authenticated()
        );

        return http.build();
    }

}