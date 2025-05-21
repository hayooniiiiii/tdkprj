package com.example.tdkprj1.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.tdkprj1.auth.PrincipalDetails;
import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.UserDto;
import com.example.tdkprj1.repository.UserRepository;
import com.auth0.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authManager,
                                  UserRepository userRepository,
                                  JwtTokenProvider tokenProvider) {
        super(authManager);
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        System.out.println("🔥 JwtAuthorizationFilter 동작 - 토큰 있음");

        String token = null;

        // ✅ 1. 쿠키에서 jwtToken 추출
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    System.out.println(token);
                    break;
                }
            }
        }


        // ✅ 3. 토큰이 없으면 다음 필터로 넘김
        if (token == null || !tokenProvider.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        // ✅ 4. 토큰에서 유저 식별 후 인증 등록
        String loginId = tokenProvider.getClaims(token)
                .getClaim("userLoginid").asString();

        Optional<TbUser> userOpt = userRepository.findByUserLoginid(loginId);
        if (userOpt.isPresent()) {
            TbUser user = userOpt.get();

            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getUserLoginid(),
                    user.getUserPassword(),
                    user.getUserName(),
                    user.getUserIpsa(),
                    user.getUserRole(),
                    user.getBuseo() != null ? user.getBuseo().getId() : null
            );

            PrincipalDetails principal = new PrincipalDetails(userDto);
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    principal, null, principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            System.out.println("🧪 인증 여부: " + auth.isAuthenticated());
            System.out.println(userDto);
        }

        chain.doFilter(request, response);
    }

}
