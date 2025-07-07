package com.jwitter.shared.config;

import com.jwitter.shared.generator.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Bean
    public SnowflakeIdGenerator idGenerator(@Value("${app.worker-id}") int workerId) {
        if (workerId < 0 || workerId >= 32) {
            throw new IllegalArgumentException("Worker ID must be between 0 and 32");
        }
        return new SnowflakeIdGenerator(workerId);
    }
}
