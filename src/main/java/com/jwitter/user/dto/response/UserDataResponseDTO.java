package com.jwitter.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDataResponseDTO {
    private LocalDateTime createdAt;
    private String id;
    private String name;
    private boolean protectedAccount;
    private String username;
    private String description;
    private String profileImageUrl;
    private UserPublicMetricsResponseDTO publicMetrics;
    private boolean verified;
    private String location;
    private LocalDateTime updatedAt;
}
