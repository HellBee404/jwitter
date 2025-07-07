package com.jwitter.tweet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetPublicMetricResponseDTO {
    private Integer bookmarkCount;
    private Integer impressionCount;
    private Integer likeCount;
    private Integer replyCount;
    private Integer retweetCount;
    private Integer quoteCount;
}
