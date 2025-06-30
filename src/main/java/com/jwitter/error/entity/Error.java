package com.jwitter.error.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwitter.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "errors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "error_internal_id")
    private UUID id;

    @Column(name = "error_api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    @ManyToOne
    @JoinColumn(name = "error_user")
    private User user;

    @Column(name = "error_title", nullable = false)
    private String title;

    @Column(name = "error_type", nullable = false)
    private String type;

    @Column(name = "error_detail")
    private String detail;

    @Column(name = "error_status")
    private Integer status;
}
