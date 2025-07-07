package com.jwitter.user.repository;

import com.jwitter.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.data.username = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tweets WHERE u.apiId = :apiId")
    User findByApiIdWithTweets(String apiId);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.data.username WHERE u.data.username = :username")
    boolean existsByDataUsername(String username);
}
