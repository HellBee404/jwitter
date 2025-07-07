package com.jwitter.user.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwitter.error.dto.response.ErrorResponseDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {
    private UserDataResponseDTO data;
    private List<ErrorResponseDTO> errors;
    private UserIncludeResponseDTO includes;
}
