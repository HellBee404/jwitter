package com.jwitter.user.mapper;

import com.jwitter.error.dto.response.ErrorResponseDTO;
import com.jwitter.error.entity.Error;
import com.jwitter.topic.dto.response.TopicResponseDTO;
import com.jwitter.topic.entity.Topic;
import com.jwitter.tweet.dto.response.TweetPublicMetricResponseDTO;
import com.jwitter.tweet.dto.response.TweetResponseDTO;
import com.jwitter.tweet.entity.Tweet;
import com.jwitter.tweet.entity.TweetPublicMetrics;
import com.jwitter.user.dto.response.UserDataResponseDTO;
import com.jwitter.user.dto.response.UserIncludeResponseDTO;
import com.jwitter.user.dto.response.UserPublicMetricsResponseDTO;
import com.jwitter.user.dto.response.UserResponseDTO;
import com.jwitter.user.entity.User;
import com.jwitter.user.entity.UserData;
import com.jwitter.user.entity.UserPublicMetrics;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "data.id", source = "apiId")
    UserResponseDTO toResponseDTO(User user);

    List<UserResponseDTO> toListResponseDTO(List<User> users);

    @Mapping(target = "id", source = "id")
    UserDataResponseDTO toDataResponseDTO(UserData userData);

    @AfterMapping
    default void enrichDTO(@MappingTarget UserDataResponseDTO dto, User user) {
        if (user.getUserPublicMetrics() != null) {
            dto.setPublicMetrics(toUserPublicMetricsResponseDTO(user.getUserPublicMetrics()));
        }
    }


    @Mapping(target = "authorId", source = "author.apiId")
    @Mapping(target = "username", source = "author.data.username")
    TweetResponseDTO toTweetResponseDTO(Tweet tweet);

    UserPublicMetricsResponseDTO toUserPublicMetricsResponseDTO(UserPublicMetrics metrics);

    TweetPublicMetricResponseDTO toTweetPublicMetricResponseDTO(TweetPublicMetrics metrics);

    ErrorResponseDTO toErrorResponseDTO(Error error);

    TopicResponseDTO toTopicResponseDTO(Topic topic);

    default UserIncludeResponseDTO mapIncludes(User user,
                                               List<Tweet> tweets,
                                               List<Topic> topics) {
        return UserIncludeResponseDTO.builder()
                .topics(topics != null ? topics.stream().map(this::toTopicResponseDTO).toList() : null)
                .tweets(tweets != null ? tweets.stream().map(this::toTweetResponseDTO).toList() : null)
                .build();
    }

    default UserResponseDTO toFullResponseDTO(User user,
                                              List<Tweet> tweets,
                                              List<Topic> topics,
                                              List<Error> errors) {
        return UserResponseDTO.builder()
                .data(toDataResponseDTO(user.getData()))
                .includes(mapIncludes(user, tweets, topics))
                .errors(errors != null ? errors.stream().map(this::toErrorResponseDTO).toList() : null)
                .build();
    }
}
