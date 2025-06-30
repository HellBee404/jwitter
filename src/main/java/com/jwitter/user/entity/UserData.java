package com.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserData {

    @Id
    @NotBlank
    @Size(min = 10, max = 30)
    @Column(name = "user_data_id", nullable = false)
    private String id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "user_data_name", nullable = false, length = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    @Column(name = "user_data_username", unique = true, nullable = false, length = 15)
    private String username;

    @Size(max = 160)
    @Column(name = "user_data_description", length = 160)
    private String description;

    @Size(max = 255)
    @Column(name = "user_data_profile_image_url", length = 255)
    private String profileImageUrl;

    @Column(name = "user_data_verified")
    private boolean verified;

    @Column(name = "user_data_protected")
    private boolean protectedAccount;

    @Column(name = "user_data_location")
    private String location;

    @CreationTimestamp
    @Column(name = "user_data_created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "user_data_updated_at")
    private LocalDateTime updatedAt;

    @Embedded
    @Column(name = "user_public_metrics")
    private UserPublicMetric userPublicMetrics;

}
