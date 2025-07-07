package com.jwitter.topic.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicResponseDTO {
    private String description;
    private String id;
    private String name;
}
