package com.trendkeyword.processor.service;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;
import com.trendkeyword.trend.repository.KeywordStatRepository;
import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.KeywordStat;
import com.trendkeyword.trend.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KeywordSnapshotProcessEventImpl implements KeywordSnapshotProcessService{

    private final KeywordRepository keywordRepository;
    private final KeywordStatRepository keywordStatRepository;

    @Override
    public void process(KeywordSnapshotEvent event) {
        LocalDateTime timeWindow = LocalDateTime.parse(event.getTimeWindow());

        log.info("[Processor] 스냅샷 처리 시작");
        log.info("처리 대상 시간 : {}", timeWindow);

        event.getKeywordCounts().forEach((value, count)->{

            log.info("키워드 처리 시작 : {} (개수 = {})", value, count);

            // 1. keyword 조회 or 생성
            Keyword keyword = keywordRepository.findByValue(value).orElseGet(() -> {
               log.info("신규 키워드 등록 : {}", value);
               Keyword k = new Keyword(value, event.getSource());
               return keywordRepository.save(k);
            });

            // 2. keyword_stat 조회
            KeywordStat stat = keywordStatRepository
                    .findByKeywordAndTimeWindow(keyword, timeWindow)
                    .orElseGet(() -> {
                        log.info("신규 통계 행 생성");
                        return keywordStatRepository.save(
                                new KeywordStat(keyword, timeWindow)
                        );
                    });

            // 3. 출처별 증가 처리 (update 역할)
            log.info("출처 통계 : {} - 카운트 {} 증가", event.getSource(), count);
            stat.increaseBySource(event.getSource(), count);
        });

        log.info("[Processor] 스냅샷 처리 완료");
    }

}
