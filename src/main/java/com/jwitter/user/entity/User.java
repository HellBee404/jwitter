package com.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwitter.error.entity.Error;
import com.jwitter.shared.entity.BaseEntity;
import com.jwitter.topic.entity.Topic;
import com.jwitter.tweet.entity.Tweet;
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
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_internal_id")
    private UUID id;

    @Column(name = "api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserCredentials credentials;

    @Embedded
    private UserData data;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Error> errors;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tweet> tweets;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Topic> topics;

    @Embedded
    private UserPublicMetrics userPublicMetrics;

    @Column(nullable = false)
    private boolean enabled;

}
