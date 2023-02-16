package com.basics.concepts.kafka.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@Accessors(chain = true)
@Validated
@Configuration
@ConfigurationProperties("kafka")
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class ClusterConfig {

    @Valid
    @NotNull
    private ClusterConfig.ClusterProperties clusterProperties;


    @Valid
    @NotNull
    private Map<String, TopicsGroupConfig> topicGroups;

    @Data
    @Accessors(chain = true)
    public static class ClusterProperties {
        @NotEmpty
        private String bootstrap;
        @NotEmpty
        private String schemaRegistry;
        @NotEmpty
        private String name;
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;
    }

    @Data
    @Accessors(chain = true)
    public static class TopicsGroupConfig {
        private ConsumerConfig consumerConfig;
        private AvroTopicConfig avroConfig;
        @Valid
        @NotEmpty
        private Map<String, TopicConfig> topics;
    }

    @Data
    @Accessors(chain = true)
    public static class TopicConfig {
        @NotEmpty
        private String name;
    }

    @Data
    @Accessors(chain = true)
    public static class ConsumerConfig {
        private String groupId;
        private String autoOffsetReset;
        private Long failureBackOffMaxTime;
        private Integer concurrency;
    }

    @Data
    @Accessors(chain = true)
    public static class AvroTopicConfig {
        private String keySubjectNameStrategy;
        private String valueSubjectNameStrategy;
    }


}
