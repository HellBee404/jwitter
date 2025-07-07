package com.jwitter.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwitter.topic.dto.response.TopicResponseDTO;
import com.jwitter.tweet.dto.response.TweetResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserIncludeResponseDTO {
    private List<TopicResponseDTO> topics;
    private List<TweetResponseDTO> tweets;
}
