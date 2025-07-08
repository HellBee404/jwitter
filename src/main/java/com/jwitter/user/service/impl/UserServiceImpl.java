package com.jwitter.user.service.impl;

import com.jwitter.topic.entity.Topic;
import com.jwitter.tweet.entity.Tweet;
import com.jwitter.user.dto.response.UserResponseDTO;
import com.jwitter.user.entity.User;
import com.jwitter.user.exception.UserNotFoundException;
import com.jwitter.user.mapper.UserMapper;
import com.jwitter.user.repository.UserRepository;
import com.jwitter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<UserResponseDTO> findByUsername(String username,
                                                          boolean includeTweets,
                                                          boolean includeTopics) {
        log.info("Starting to find user by username: {}", username);
        log.debug("Include tweets: {}, Include topics: {}", includeTweets, includeTopics);

        try {
            User user = loadUserWithSelectedRelations(username, includeTweets, includeTopics);
            log.debug("Successfully loaded user: {}", user.getData().getUsername());

            List<Tweet> tweets = includeTweets ? user.getTweets() : null;
            List<Topic> topics = includeTopics ? user.getTopics() : null;

            if (includeTweets) {
                log.debug("Loaded {} tweets for user {}",
                        tweets != null ? tweets.size() : 0, username);
            }
            if (includeTopics) {
                log.debug("Loaded {} topics for user {}",
                        topics != null ? topics.size() : 0, username);
            }

            UserResponseDTO responseDTO = userMapper.toFullResponseDTO(user, tweets, topics, null);
            log.info("Successfully created response DTO for user: {}", username);

            return ResponseEntity.ok(responseDTO);
        } catch (UserNotFoundException e) {
            log.warn("User not found: {}", username);
            throw e;
        } catch (Exception e) {
            log.error("Error while finding user by username: {}", username, e);
            throw e;
        }
    }

    @Override
    public ResponseEntity<UserResponseDTO> findByUsername(String username,
                                                          List<String> includes,
                                                          boolean includeUserMetrics,
                                                          boolean includeTweetMetrics) {
        log.info("Starting to find user by username: {}", username);
        log.debug("Includes: {}", includes);
        log.debug("IncludeUserMetrics: {}", includeUserMetrics);
        log.debug("IncludeTweetMetrics: {}", includeTweetMetrics);

        try {
            boolean includeTweets = includes != null && includes.contains("tweets");
            boolean includeTopics = includes != null && includes.contains("topics");
            log.debug("Include tweets: {}, Include topics: {}", includeTweets, includeTopics);

            User user = loadUserWithSelectedRelations(username, includeTweets, includeTopics);
            log.debug("Successfully loaded user: {}", user.getData().getUsername());

            List<Tweet> tweets = includeTweets ? user.getTweets() : null;
            List<Topic> topics = includeTopics ? user.getTopics() : null;

            if (includeTweets) {
                log.debug("Loaded {} tweets for user {}",
                        tweets != null ? tweets.size() : 0, username);
            }
            if (includeTopics) {
                log.debug("Loaded {} topics for user {}",
                        topics != null ? topics.size() : 0, username);
            }

            UserResponseDTO responseDTO = userMapper.toFullResponseDTO(
                    user,
                    tweets,
                    topics,
                    null,
                    includeUserMetrics,
                    includeTweetMetrics);
            log.info("Successfully created response DTO for user: {}", username);

            return ResponseEntity.ok(responseDTO);
        } catch (UserNotFoundException e) {
            log.warn("User not found: {}", username);
            throw e;
        } catch (Exception e) {
            log.error("Error while finding user by username: {}", username, e);
            throw e;
        }
    }

    private User loadUserWithSelectedRelations(String username,
                                               boolean includeTweets,
                                               boolean includeTopics) {
        log.debug("Loading user with relations - username: {}, tweets: {}, topics: {}",
                username, includeTweets, includeTopics);

        if (includeTweets && includeTopics) {
            log.debug("Loading user with both tweets and topics");
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        log.warn("User not found in repository: {}", username);
                        return new UserNotFoundException("User not found: " + username);
                    });

            if (includeTweets) {
                log.debug("Initializing tweets collection for user: {}", username);
                Hibernate.initialize(user.getTweets());
                log.debug("Tweets initialized, count: {}",
                        user.getTweets() != null ? user.getTweets().size() : 0);
            }
            if (includeTopics) {
                log.debug("Initializing topics collection for user: {}", username);
                Hibernate.initialize(user.getTopics());
                log.debug("Topics initialized, count: {}",
                        user.getTopics() != null ? user.getTopics().size() : 0);
            }
            return user;
        } else if (includeTweets) {
            log.debug("Loading user with tweets only");
            return userRepository.findByUsernameWithTweets(username)
                    .orElseThrow(() -> {
                        log.warn("User with tweets not found: {}", username);
                        return new UserNotFoundException("User not found: " + username);
                    });
        } else if (includeTopics) {
            log.debug("Loading user with topics only");
            return userRepository.findByUsernameWithTopics(username)
                    .orElseThrow(() -> {
                        log.warn("User with topics not found: {}", username);
                        return new UserNotFoundException("User not found: " + username);
                    });
        } else {
            log.debug("Loading basic user data without relations");
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> {
                        log.warn("Basic user data not found: {}", username);
                        return new UserNotFoundException("User not found: " + username);
                    });
        }
    }
}
