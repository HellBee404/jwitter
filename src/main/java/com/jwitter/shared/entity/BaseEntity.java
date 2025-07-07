package com.jwitter.shared.entity;

import com.jwitter.shared.config.ApplicationContextHolder;
import com.jwitter.shared.generator.SnowflakeIdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@MappedSuperclass
@Slf4j
@Getter
@Setter
public abstract class BaseEntity {

    @Column(name = "api_id", unique = true, nullable = false, length = 30)
    private String apiId;

    public void generateApiId() {
        log.debug("Generating apiId for entity");
        if (this.apiId == null) {
            SnowflakeIdGenerator generator = ApplicationContextHolder.getBean(SnowflakeIdGenerator.class);
            log.debug("Retrieved generator: {}", generator);
            this.apiId = generator.generate();
            log.debug("Generated apiId: {}", this.apiId);
        }
    }
}
