package com.jwitter.tweet.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tweet_internal_id")
    private UUID id;

    @Column(name = "tweet_api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    @Column(name = "tweet_author_id", unique = true, length = 30)
    private String authorId;

    @Column(name = "tweet_community_id")
    private String communityId;

    @CreationTimestamp
    @Column(name = "tweet_data_created_at", updatable = false)
    @DateTimeFormat
    private LocalDateTime createdAt;

    @Column(name = "tweet_lang")
    private String language;

    @Embedded
    @Column(name = "tweet_public_metrics")
    private TweetPublicMetric publicMetrics;

    @Column(name = "tweet_text")
    private String text;

    @Column(name = "tweet_username")
    private String username;
}
