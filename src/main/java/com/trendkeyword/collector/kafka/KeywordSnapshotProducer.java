package com.trendkeyword.collector.kafka;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeywordSnapshotProducer {

    private static final String TOPIC = "keyword.snapshot";

    private final KafkaTemplate<String, KeywordSnapshotEvent> kafkaTemplate;

    public void send(KeywordSnapshotEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.getSource(),
                event
        );
    }
}
