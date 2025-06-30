package com.jwitter.tweet.entity;

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
public class TweetPublicMetric {
    @Column(name = "tweet_bookmark_count", nullable = false)
    private Integer bookmarkCount;

    @Column(name = "tweet_impression_count", nullable = false)
    private Integer impressionCount;

    @Column(name = "tweet_like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "tweet_reply_count", nullable = false)
    private Integer replyCount;

    @Column(name = "tweet_retweet_count", nullable = false)
    private Integer retweetCount;

    @Column(name = "tweet_quote_count")
    private Integer quoteCount;
}
