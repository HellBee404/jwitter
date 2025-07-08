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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "data.id", source = "apiId")
    UserResponseDTO toResponseDTO(User user);

    List<UserResponseDTO> toListResponseDTO(List<User> users);

    @Mapping(target = "id", source = "id")
    UserDataResponseDTO toDataResponseDTO(UserData userData);

//    @AfterMapping
//    default void enrichDTO(@MappingTarget UserDataResponseDTO dto, User user) {
//        if (user.getUserPublicMetrics() != null) {
//            dto.setPublicMetrics(toUserPublicMetricsResponseDTO(user.getUserPublicMetrics()));
//        }
//    }


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

    default UserResponseDTO toFullResponseDTO(User user,
                                              List<Tweet> tweets,
                                              List<Topic> topics,
                                              List<Error> errors,
                                              boolean includeUserMetrics,
                                              boolean includeTweetMetrics) {

        UserDataResponseDTO data = toDataResponseDTO(user.getData());

        if (includeUserMetrics && user.getUserPublicMetrics() != null) {
            data.setPublicMetrics(toUserPublicMetricsResponseDTO(user.getUserPublicMetrics()));
        }

        UserIncludeResponseDTO includes = UserIncludeResponseDTO.builder()
                .tweets(includeTweets(tweets, includeTweetMetrics))
                .topics(includeTopics(topics))
                .build();

        return UserResponseDTO.builder()
                .data(data)
                .includes(shouldInclude(includes) ? includes : null)
                .errors(errors != null && !errors.isEmpty() ?
                        errors.stream().map(this::toErrorResponseDTO).toList() : null)
                .build();
    }

    private List<TweetResponseDTO> includeTweets(List<Tweet> tweets, boolean includeMetrics) {
        if (tweets == null) return null;

        return tweets.stream()
                .map(this::toTweetResponseDTO)
                .peek(dto -> {
                    if (!includeMetrics) dto.setPublicMetrics(null);
                })
                .toList();
    }

    private List<TopicResponseDTO> includeTopics(List<Topic> topics) {
        if (topics == null) return null;
        return topics.stream().map(this::toTopicResponseDTO).toList();
    }

    private boolean shouldInclude(UserIncludeResponseDTO includes) {
        return includes.getTweets() != null || includes.getTopics() != null;
    }
//    default UserResponseDTO toFullResponseDTO(User user,
//                                              List<Tweet> tweets,
//                                              List<Topic> topics,
//                                              List<Error> errors,
//                                              boolean includeUserMetrics,
//                                              boolean includeTweetMetrics) {
//
//        UserDataResponseDTO data = toDataResponseDTO(user.getData());
//
//        if (includeUserMetrics && user.getUserPublicMetrics() != null) {
//            data.setPublicMetrics(toUserPublicMetricsResponseDTO(user.getUserPublicMetrics()));
//        }
//
//        List<TweetResponseDTO> tweetDTOs = null;
//        if (tweets != null) {
//            tweetDTOs = tweets.stream()
//                    .map(this::toTweetResponseDTO)
//                    .peek(dto -> {
//                        if (!includeTweetMetrics) {
//                            dto.setPublicMetrics(null);
//                        }
//                    })
//                    .toList();
//        }
//
//        return UserResponseDTO.builder()
//                .data(data)
//                .includes(UserIncludeResponseDTO.builder()
//                        .tweets(tweetDTOs)
//                        .topics(topics != null ? topics.stream().map(this::toTopicResponseDTO).toList() : null)
//                        .build())
//                .errors(errors != null ? errors.stream().map(this::toErrorResponseDTO).toList() : null)
//                .build();
//    }

}
