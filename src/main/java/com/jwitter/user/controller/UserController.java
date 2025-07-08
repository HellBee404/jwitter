package com.jwitter.user.controller;

import com.jwitter.user.dto.response.UserResponseDTO;
import com.jwitter.user.mapper.UserMapper;
import com.jwitter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/short/{username}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String username,
                                                   @RequestParam(required = false) boolean includeTweets,
                                                   @RequestParam(required = false) boolean includeTopics) {
        return userService.findByUsername(username, includeTweets, includeTopics);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username,
                                                             @RequestParam(required = false) List<String> includes,
                                                             @RequestParam(defaultValue = "false") boolean includeUserMetrics,
                                                             @RequestParam(defaultValue = "false") boolean includeTweetMetrics) {
        return userService.findByUsername(username, includes, includeUserMetrics, includeTweetMetrics);
    }
}
