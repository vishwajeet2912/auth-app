package com.Substring.auth.Security;

import com.Substring.auth.helpers.UserHelper;
import com.Substring.auth.repositories.UserRespository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRespository userRespository;

    // ✅ FIXED LOGGER TYPE
    private static final Logger logger =
            LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        logger.info("Authorization header : {}", header);

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            if (!jwtService.isAccessToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            try {

                Jws<Claims> parse = jwtService.parse(token);
                Claims payload = parse.getBody();

                String userId = payload.getSubject();
                UUID userUuid = UserHelper.parseUUID(userId);

                userRespository.findById(userUuid)
                        .ifPresent(user -> {

                            if (!user.isEnable()) {
                                return;
                            }

                            List<SimpleGrantedAuthority> authorities =
                                    user.getRoles().stream()
                                            .map(role ->
                                                    new SimpleGrantedAuthority(role.getName()))
                                            .toList();

                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(
                                            user.getEmail(),
                                            null,
                                            authorities
                                    );

                            authenticationToken.setDetails(
                                    new WebAuthenticationDetailsSource()
                                            .buildDetails(request)
                            );

                            if (SecurityContextHolder.getContext()
                                    .getAuthentication() == null) {

                                SecurityContextHolder.getContext()
                                        .setAuthentication(authenticationToken);
                            }
                        });

            } catch (JwtException e) {
                logger.error("JWT Error: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/v1/auth");
    }
}