package com.basics.concepts.kafka;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "kafka.enabled", havingValue = "true", matchIfMissing = true)
@KafkaListener(topics = "#{clusterConfigHelper.getTopicNamesForGroup('some-consumer-group')}",
        containerFactory = "topicListenerContainer")
public class KafkaConsumer {

    private static final String TOPIC = "some-topic-name";

    public KafkaConsumer() {

    }

    @KafkaHandler(isDefault = true)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void handleEvent(
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            SpecificRecordBase event
    ) {
        if (topic.equals(TOPIC)) {
            //TODO: Cast the event to a specific avro object.
            //TODO: Add some logic.
        }

    }


}
