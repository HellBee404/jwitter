package com.hellbee.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hellbee.jwitter.error.entity.Error;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_internal_id")
    private UUID id;

    @Column(name = "user_api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserCredentials credentials;

    @Embedded
    private UserData data;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Error> errors;

    @Embedded
    private UserInclude includes;

    @Column(nullable = false)
    private boolean enabled;
}
