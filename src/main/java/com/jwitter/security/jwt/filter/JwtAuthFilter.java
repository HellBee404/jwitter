package com.jwitter.security.jwt.filter;

import com.jwitter.security.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No JWT token found in request headers");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwtToken = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwtToken);
            log.debug("Extracted username '{}' from JWT (request IP: {})", username, request.getRemoteAddr());

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                log.debug("Loaded user details for: {}", username);

                if (!jwtService.isTokenValid(jwtToken, userDetails)) {
                    log.warn("Invalid JWT token for user: {}", username);
                    filterChain.doFilter(request, response);
                    return;
                }

                if (jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Authenticated user: {}", username);
                } else {
                    log.warn("Invalid JWT token for user: {}", username);
                }
            }
        } catch (Exception e) {
            log.error("JWT authentication failed for request from {}: {}",
                    request.getRemoteAddr(), e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
