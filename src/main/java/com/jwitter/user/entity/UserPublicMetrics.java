package com.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//@Entity
//@Table(name = "user_public_metrics")
@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPublicMetrics {

    @NotBlank
    @Size(min = 10, max = 30)
    @Column(name = "user_metrics_data_id", nullable = false)
    private String id;

    @Column(name = "user_followers_count", nullable = false)
    private Integer followersCount = 0;

    @Column(name = "user_following_count", nullable = false)
    private Integer followingCount = 0;

    @Column(name = "user_tweet_count", nullable = false)
    private Integer tweetCount = 0;

    @Column(name = "user_listed_count", nullable = false)
    private Integer listedCount = 0;

    @Column(name = "user_like_count")
    private Integer likeCount = 0;
}
