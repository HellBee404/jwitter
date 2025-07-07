package com.jwitter.auth.service;

import com.jwitter.auth.dto.request.RegisterRequest;
import com.jwitter.auth.dto.response.JWTTokenResponse;
import com.jwitter.security.jwt.service.JwtService;
import com.jwitter.shared.generator.SnowflakeIdGenerator;
import com.jwitter.user.entity.*;
import com.jwitter.user.exception.ConflictException;
import com.jwitter.user.repository.UserCredentialsRepository;
import com.jwitter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RegisterService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final JwtService jwtService;

    @Transactional
    public ResponseEntity<JWTTokenResponse> register(@RequestBody RegisterRequest request) {
        log.info("Registering new user: {}", request.getUsername());

        validateUsernameAndEmail(request);

        User user = buildUserFromRequest(request);
        userRepository.save(user);
        log.debug("User saved: {}", user.getData().getUsername());

        UserCredentials credentials = buildCredentials(user, request.getPassword());
        userCredentialsRepository.save(credentials);
        log.debug("User credentials saved for: {}", user.getData().getUsername());

        String token = jwtService.generateTokenByUser(user);
        log.info("JWT token generated for: {}", user.getData().getUsername());

        return ResponseEntity.ok(new JWTTokenResponse(token));
    }

    private void validateUsernameAndEmail(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Username {} already exists", request.getUsername());
            throw new ConflictException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email {} already exists", request.getEmail());
            throw new ConflictException("Email already exists");
        }
    }

    private User buildUserFromRequest(RegisterRequest request) {
        return User.builder()
                .apiId(generateId())
                .role(Role.USER)
                .data(UserData.builder()
                        .id(generateId())
                        .name(request.getName() != null ? request.getName() : ("User" + generateId()))
                        .username(request.getUsername())
                        .email(request.getEmail())
                        .build())
                .userPublicMetrics(UserPublicMetrics.builder()
                        .id(generateId())
                        .followersCount(0)
                        .followingCount(0)
                        .tweetCount(0)
                        .listedCount(0)
                        .likeCount(0)
                        .build())
                .enabled(true)
                .build();
    }

    private UserCredentials buildCredentials(User user, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword);
        return UserCredentials.builder()
                .user(user)
                .passwordHash(hashed)
                .apiId(generateId())
                .build();
    }

    private String generateId() {
        return snowflakeIdGenerator.generate();
    }
}
