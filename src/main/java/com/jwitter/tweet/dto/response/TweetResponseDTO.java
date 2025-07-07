package com.jwitter.tweet.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetResponseDTO {
    private String authorId;
    private LocalDateTime createdAt;
    private String id;
    private String text;
    private String username;
    private TweetPublicMetricResponseDTO publicMetrics;
    private String communityId;
    private String language;
}
