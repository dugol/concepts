package com.basics.concepts.kafka.config;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import lombok.Data;
import lombok.NonNull;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;

import java.util.HashMap;
import java.util.Map;

public final class ClusterConnectionConfig {

    private static final String SCRAM_SHA_512 = "SCRAM-SHA-512";
    private static final String SASL_SSL = "SASL_SSL";
    private static final String SASL_INHERIT = "SASL_INHERIT";
    private static final String SASL_JAAS_CONFIG_TEMPLATE =
            "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";

    /**
     * Provides defaults for a connection to a cluster.
     *
     * @param cluster    the named cluster
     * @param configType the type of client
     * @return properties for cluster connection
     */
    @NonNull
    public static Map<String, String> brokerAndSchemaRegistryConfig(
            Cluster cluster, ClientType configType) {
        Map<String, String> vals = new HashMap<>();
        vals.put(configType.bootstrapConfig, cluster.bootstrap);
        vals.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, cluster.schemaRegistry);
        return vals;
    }

    /**
     * Provides defaults for a secure connection to a cluster.
     *
     * @return properties for secure connection
     */
    @NonNull
    public static Map<String, String> secureConfig(String accessKey, String secretKey) {
        Map<String, String> defaults = new HashMap<>();

        // configuration for clusters that uses SASL_SSL for the protocol
        defaults.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, SASL_SSL);
        // configuration for clusters that uses SCRAM-SHA-512 for client connections
        defaults.put(SaslConfigs.SASL_MECHANISM, SCRAM_SHA_512);
        // configuration for clusters that inherit the SASL config to talk to the schema registry
        defaults.put(AbstractKafkaSchemaSerDeConfig.BASIC_AUTH_CREDENTIALS_SOURCE, SASL_INHERIT);
        // configuration for clusters that uses accesskey/secretkey pairs for authentication
        defaults.put(
                SaslConfigs.SASL_JAAS_CONFIG,
                String.format(SASL_JAAS_CONFIG_TEMPLATE, accessKey, secretKey));

        return defaults;
    }

    public enum ClientType {
        PRODUCER(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG),
        CONSUMER(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG);

        private final String bootstrapConfig;

        ClientType(String bootstrapConfig) {
            this.bootstrapConfig = bootstrapConfig;
        }
    }

    @Data
    public static class Cluster {

        private final String bootstrap;
        private final String schemaRegistry;

        Cluster(String bootstrap, String schemaRegistry) {
            this.bootstrap = bootstrap;
            this.schemaRegistry = schemaRegistry;
        }
    }
}
