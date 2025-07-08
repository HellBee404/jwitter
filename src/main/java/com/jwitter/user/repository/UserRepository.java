package com.jwitter.user.repository;

import com.jwitter.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.data.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tweets WHERE u.data.username = :username")
    Optional<User> findByUsernameWithTweets(@Param("username") String username);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.topics WHERE u.data.username = :username")
    Optional<User> findByUsernameWithTopics(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.data.username = :username")
    @EntityGraph(attributePaths = {"tweets"})
    Optional<User> findWithTweetsByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.data.username = :username")
    @EntityGraph(attributePaths = {"topics"})
    Optional<User> findWithTopicsByUsername(@Param("username") String username);

    @Query("SELECT u.id FROM User u WHERE u.data.username = :username")
    Optional<UUID> findIdByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.data.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.data.username = :username")
    boolean existsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.data.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tweets WHERE u.apiId = :apiId")
    Optional<User> findByApiIdWithTweets(@Param("apiId") String apiId);

    Optional<User> findByApiId(String apiId);

}
