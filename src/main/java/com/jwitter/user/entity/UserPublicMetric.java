package com.hellbee.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPublicMetric {

    @Column(name = "user_followers_count", nullable = false)
    private Integer followersCount;

    @Column(name = "user_following_count", nullable = false)
    private Integer followingCount;

    @Column(name = "user_tweet_count", nullable = false)
    private Integer tweetCount;

    @Column(name = "user_listed_count", nullable = false)
    private Integer listedCount;

    @Column(name = "user_like_count")
    private Integer likeCount;
}
