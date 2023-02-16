package com.basics.concepts.kafka.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.util.backoff.ExponentialBackOff;

import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS;
import static org.springframework.kafka.support.serializer.ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS;

@EnableKafka
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true", matchIfMissing = true)
public class KafkaConsumerConfig {

    public static final String STORE_COMMERCE_GROUP = "store-commerce-returns";

    private final ClusterConfig clusterConfig;
    private final ClusterConfigHelper clusterConfigHelper;

    @Bean
    public DefaultKafkaConsumerFactory<String, SpecificRecord> topicConsumerFactory() {
        var topicGroup = clusterConfig.getTopicGroups().get(STORE_COMMERCE_GROUP);
        var consumerProps = topicGroup.getConsumerConfig();

        var props = clusterConfigHelper.buildCommonConfig(topicGroup);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerProps.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProps.getAutoOffsetReset());

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        props.put(
                KEY_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class.getName());
        props.put(
                VALUE_DESERIALIZER_CLASS, ErrorHandlingDeserializer.class.getName());

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SpecificRecord>
    topicListenerContainer(
            ConsumerFactory<String, SpecificRecord> topicConsumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, SpecificRecord>();
        factory.setConsumerFactory(topicConsumerFactory);
        var consumerConfig = clusterConfig.getTopicGroups().get(STORE_COMMERCE_GROUP).getConsumerConfig();
        factory.setConcurrency(consumerConfig.getConcurrency());

        var backOff = new ExponentialBackOff();
        backOff.setMaxElapsedTime(consumerConfig.getFailureBackOffMaxTime());

        factory.setCommonErrorHandler(
                new DefaultErrorHandler(
                        (record, exception) -> {
                            //TODO: Add some loggin error.
                        },
                        backOff));
        return factory;
    }
}
