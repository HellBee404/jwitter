package com.jwitter.user.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jwitter.tweet.entity.Tweet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInclude {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "user_includes_tweets")
    private List<Tweet> tweets;

    @Embedded
    @Column(name = "user_includes_topics")
    private UserIncludeTopic topics;
}
