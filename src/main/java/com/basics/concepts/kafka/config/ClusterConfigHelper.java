package com.basics.concepts.kafka.config;

import com.basics.concepts.kafka.config.ClusterConnectionConfig.ClientType;
import com.basics.concepts.kafka.config.ClusterConnectionConfig.Cluster;
import com.basics.concepts.kafka.config.ClusterConfig.AvroTopicConfig;
import com.basics.concepts.kafka.config.ClusterConfig.TopicConfig;
import com.basics.concepts.kafka.config.ClusterConfig.TopicsGroupConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Component
@RequiredArgsConstructor
@ConditionalOnBean(ClusterConfig.class)
public class ClusterConfigHelper {

    private final ClusterConfig clusterConfig;

    public Map<String, Object> buildCommonConfig(TopicsGroupConfig topicsGroup) {
        var props = new HashMap<String, Object>();

        ClusterConfig.ClusterProperties clusterPropertiesCredentials = clusterConfig.getClusterProperties();
        if (isNull(clusterPropertiesCredentials)) {
            throw new IllegalArgumentException("A specified cluster is not configured: " + clusterPropertiesCredentials.getName());
        }
        props.putAll(
                ClusterConnectionConfig.secureConfig(clusterPropertiesCredentials.getUsername(), clusterPropertiesCredentials.getPassword()));
        props.putAll(ClusterConnectionConfig.brokerAndSchemaRegistryConfig(
                new Cluster(clusterPropertiesCredentials.getBootstrap(), clusterPropertiesCredentials.getSchemaRegistry()), ClientType.CONSUMER));

        AvroTopicConfig avroConfig = topicsGroup.getAvroConfig();
        props.put(
                KafkaAvroSerializerConfig.KEY_SUBJECT_NAME_STRATEGY,
                requireNonNull(avroConfig.getKeySubjectNameStrategy()));
        props.put(
                KafkaAvroSerializerConfig.VALUE_SUBJECT_NAME_STRATEGY,
                requireNonNull(avroConfig.getValueSubjectNameStrategy()));
        props.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, Boolean.FALSE.toString());

        return props;
    }

    public List<String> getTopicNamesForGroup(String groupName) {
        TopicsGroupConfig topicsGroup = clusterConfig.getTopicGroups().get(groupName);
        return topicsGroup.getTopics().values().stream()
                .map(TopicConfig::getName)
                .collect(Collectors.toList());
    }
}
