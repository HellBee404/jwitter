package com.jwitter.tweet.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwitter.shared.entity.BaseEntity;
import com.jwitter.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tweets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tweet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tweet_internal_id")
    private UUID id;

    @Column(name = "api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    @ManyToOne
    @JoinColumn(name = "tweet_author", nullable = false)
    private User author;

    @Column(name = "tweet_community_id")
    private String communityId;

    @CreationTimestamp
    @Column(name = "tweet_data_created_at", updatable = false)
    @DateTimeFormat
    private LocalDateTime createdAt;

    @Column(name = "tweet_lang")
    private String language;

    @Embedded
    private TweetPublicMetrics publicMetrics;

    @Column(name = "tweet_text")
    private String text;

    @Column(name = "tweet_username")
    private String username;

}
