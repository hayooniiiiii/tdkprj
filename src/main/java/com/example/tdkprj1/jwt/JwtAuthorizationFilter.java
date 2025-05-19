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
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");
        if (!tokenProvider.validateToken(token)) {
            chain.doFilter(request, response);
            return;
        }

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
        }
        chain.doFilter(request, response);
    }
}
