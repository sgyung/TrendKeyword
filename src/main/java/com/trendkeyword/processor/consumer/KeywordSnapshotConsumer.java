package com.trendkeyword.processor.consumer;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;
import com.trendkeyword.processor.service.KeywordSnapshotProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KeywordSnapshotConsumer {

    private final KeywordSnapshotProcessService keywordSnapshotProcessService;

    @KafkaListener(topics = "keyword.snapshot", groupId = "trend-processor")
    public void consume(KeywordSnapshotEvent event) {
        log.info("[Processor] 스냅샷 이벤트 수신");
        log.info("시간 구간 : {}", event.getTimeWindow());
        log.info("키워드 개수 : {}", event.getKeywordCounts().size());

        keywordSnapshotProcessService.process(event);
    }
}
