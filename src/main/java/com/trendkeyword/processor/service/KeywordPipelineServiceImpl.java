package com.trendkeyword.processor.service;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;
import com.trendkeyword.sentiment.service.SentimentService;
import com.trendkeyword.trend.service.TrendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KeywordPipelineServiceImpl implements KeywordPipelineService {

    private final KeywordSnapshotProcessService snapshotProcessService;
    private final TrendService  trendService;
    private final SentimentService sentimentService;


    @Override
    public void handle(KeywordSnapshotEvent event) {
        LocalDateTime timeWindow = LocalDateTime.parse(event.getTimeWindow());

        log.info("[Pipeline] 1. 통계 적재 시작");
        snapshotProcessService.process(event);

        log.info("[Pipeline] 2. 트렌드 계산 시작");
        trendService.calculateTrends(timeWindow);

//        log.info("[Pipeline] 3. 감정 분석 시작");
//        sentimentService.analyzeSentiment(timeWindow);

        log.info("[Pipeline] 전체 파이프라인 완료");
    }
}
