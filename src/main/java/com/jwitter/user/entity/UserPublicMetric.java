package com.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
//@Table(name = "user_public_metrics")
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPublicMetric {

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "user_metrics_internal_id")
//    private UUID id;

    @NotBlank
    @Size(min = 10, max = 30)
    @Column(name = "user_metrics_data_id", nullable = false)
    private String id;

//    @Column(name = "user_metrics_api_id", unique = true, nullable = false, length = 30)
//    private String apiId;

//    @ManyToOne
//    @JoinColumn(name = "user_metrics")
//    private User user;

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
