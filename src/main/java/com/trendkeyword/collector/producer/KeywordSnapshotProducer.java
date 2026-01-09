package com.trendkeyword.collector.producer;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;
import com.trendkeyword.collector.aggregate.KeywordSnapshotService;
import com.trendkeyword.common.config.KeywordSnapshotKafkaProducer;
import com.trendkeyword.trend.domain.KeywordSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KeywordSnapshotProducer {

    private final KeywordSnapshotService snapshotService;
    private final KeywordSnapshotKafkaProducer kafkaProducer;

    private static final int SNAPSHOT_LIMIT = 50;

    /**
     * Redis 집계 결과를 스냅샷 이벤트로 만들어 Kafka에 발행
     */
    public void produce(
            String redisKey,
            String timeWindow,
            KeywordSource source
    ) {
        Map<String, Integer> snapshot = loadSnapshot(redisKey, SNAPSHOT_LIMIT);

        if (isEmpty(snapshot)) {
            return;
        }

        KeywordSnapshotEvent event = createEvent(timeWindow, source, snapshot);
        sendToKafka(event);
    }

    // =========================
    // private methods
    // =========================

    /**
     * Redis에서 스냅샷 조회
     */
    private Map<String, Integer> loadSnapshot(String redisKey, int limit) {
        return snapshotService.getSnapshot(redisKey,  limit);
    }

    /**
     * 스냅샷 비어있는지 검증
     */
    private boolean isEmpty(Map<String, Integer> snapshot) {
        return snapshot == null || snapshot.isEmpty();
    }

    /**
     * 스냅샷 → 이벤트 변환
     */
    private KeywordSnapshotEvent createEvent(
            String timeWindow,
            KeywordSource source,
            Map<String, Integer> snapshot
    ) {
        return new KeywordSnapshotEvent(timeWindow, source, snapshot);
    }

    /**
     * Kafka로 이벤트 전송
     */
    private void sendToKafka(KeywordSnapshotEvent event) {
        kafkaProducer.send(event);
    }
}
