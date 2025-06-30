package com.hellbee.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UserIncludeTopic {
    @Id
    @NotBlank
    @Size(min = 10, max = 30)
    @Column(name = "user_include_topic_id", nullable = false)
    private String id;

    @Column(name = "user_include_topic_name", nullable = false)
    private String name;

    @Column(name = "user_include_topic_description")
    private String description;
}
