package com.jwitter.user;

import com.jwitter.shared.generator.SnowflakeIdGenerator;
import com.jwitter.user.entity.User;
import com.jwitter.user.entity.UserData;
import com.jwitter.user.entity.UserPublicMetrics;
import com.jwitter.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@EntityScan(basePackages = {
        "com.jwitter.user.entity",
        "com.jwitter.error.entity",
        "com.jwitter.topic.entity",
        "com.jwitter.tweet.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.jwitter.user.repository",
        "com.jwitter.tweet.repository"
})
class UserPersistenceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public SnowflakeIdGenerator snowflakeIdGenerator() {
            return new SnowflakeIdGenerator(1);
        }
    }

    @Test
    @Transactional
    void shouldGenerateAndPersistUserCorrectly() {
        User user = User.builder()
                .data(UserData.builder()
                        .username("test_user")
                        .name("Test User")
                        .build())
                .enabled(true)
                .userPublicMetrics(UserPublicMetrics.builder()
                        .followersCount(0)
                        .followingCount(0)
                        .tweetCount(0)
                        .likeCount(0)
                        .listedCount(0)
                        .build())
                .build();

        User saved = userRepository.saveAndFlush(user);
        entityManager.clear();

        assertThat(saved.getApiId())
                .isNotNull()
                .hasSizeBetween(10, 30)
                .containsOnlyDigits();
    }
}