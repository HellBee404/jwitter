package com.jwitter.user.service;

import com.jwitter.user.dto.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ResponseEntity<UserResponseDTO> findByUsername(String username, boolean includeTweets, boolean includeTopics);

    ResponseEntity<UserResponseDTO> findByUsername(String username, List<String> includes, boolean includeTweetMetrics, boolean includeTweetMetrics1);
}
