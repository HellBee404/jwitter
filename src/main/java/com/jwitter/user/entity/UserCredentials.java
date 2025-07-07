package com.jwitter.user.entity;

import com.jwitter.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_credentials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredentials extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_credentials_id")
    private UUID id;

    @Column(name = "api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "user_credentials_password_hash", nullable = false)
    private String passwordHash;

    @UpdateTimestamp
    @Column(name = "user_credentials_updated_at", nullable = false)
    private LocalDateTime lastUpdated;
}
