package com.jwitter.auth.service;

import com.jwitter.auth.dto.request.AuthRequest;
import com.jwitter.auth.dto.response.JWTTokenResponse;
import com.jwitter.security.jwt.service.JwtService;
import com.jwitter.user.entity.User;
import com.jwitter.user.entity.UserCredentials;
import com.jwitter.user.exception.UserNotFoundException;
import com.jwitter.user.repository.UserCredentialsRepository;
import com.jwitter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public ResponseEntity<JWTTokenResponse> login(AuthRequest request) {
        log.info("Login attempt: {}", request.getUsernameOrEmail());

        User user = findUserByUsernameOrEmail(request.getUsernameOrEmail());

        if (user == null) {
            throw new UserNotFoundException("User not found: " + request.getUsernameOrEmail());
        }

        if (!user.isEnabled()) {
            throw new IllegalStateException("User is disabled");
        }

        log.debug("User found: {}", user.getData().getUsername());

        UserCredentials credentials = userCredentialsRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User has no saved password"));

        if (!passwordEncoder.matches(request.getPassword(), credentials.getPasswordHash())) {
            log.warn("Invalid password for user: {}", user.getData().getUsername());
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = jwtService.generateTokenByUser(user);
        log.info("Login successful for user: {}", user.getData().getUsername());

        return ResponseEntity.ok(new JWTTokenResponse(token));
    }

    private User findUserByUsernameOrEmail(String value) {
        return userRepository.findByUsername(value)
                .or(() -> userRepository.findByEmail(value))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + value));
    }
}
