package com.trendkeyword.collector.publisher;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;
import com.trendkeyword.collector.kafka.KeywordSnapshotProducer;
import com.trendkeyword.collector.redis.KeywordSnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KeywordSnapshotPublisher {

    private final KeywordSnapshotService snapshotService;
    private final KeywordSnapshotProducer producer;

    /**
     * Redis 집계 결과를 스냅샷 이벤트로 만들어 Kafka에 발행
     */
    public void publish(
            String redisKey,
            LocalDateTime timeWindow,
            String source
    ) {
        Map<String, Integer> snapshot =
                snapshotService.getSnapshot(redisKey);

        if (snapshot.isEmpty()) {
            return;
        }

        KeywordSnapshotEvent event =
                new KeywordSnapshotEvent(timeWindow, source, snapshot);

        producer.send(event);
    }
}
