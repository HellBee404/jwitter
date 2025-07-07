package com.jwitter.user.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPublicMetricsResponseDTO {
    private Integer followersCount;
    private Integer followingCount;
    private Integer tweetCount;
    private Integer listedCount;
    private Integer likeCount;
}
