package com.jwitter.topic.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwitter.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "topics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "topic_internal_id")
    private UUID id;

    @Column(name = "topic_api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    @ManyToOne
    @JoinColumn(name = "topic_user")
    private User user;

    @Column(name = "topic_name", nullable = false)
    private String name;

    @Column(name = "topic_description")
    private String description;

}
