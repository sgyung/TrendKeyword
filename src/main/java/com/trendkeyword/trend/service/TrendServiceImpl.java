package com.trendkeyword.trend.service;

import com.trendkeyword.trend.domain.KeywordStat;
import com.trendkeyword.trend.domain.Trend;
import com.trendkeyword.trend.repository.KeywordStatRepository;
import com.trendkeyword.trend.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TrendServiceImpl implements TrendService {

    private final KeywordStatRepository keywordStatRepository;
    private final TrendRepository trendRepository;

    @Override
    public void calculateTrends(LocalDateTime timeWindow) {
        log.info("[Trend] 트렌드 계산 시작 - {}", timeWindow);

        List<KeywordStat> stats =
                keywordStatRepository.findAllByTimeWindow(timeWindow);

        int rank = 1;

        for (KeywordStat stat : stats) {

            // 1. 최소 기준 필터
            if (stat.getTotalCount() < 10) {
                continue;
            }

            // 2. 지금은 단순 로직 (추후 고도화)
            double growthRate = 0.0;
            double trendScore = stat.getTotalCount(); // 임시 점수

            Trend trend = new Trend(
                    stat.getKeyword(),
                    timeWindow,
                    growthRate,
                    trendScore,
                    rank++
            );

            trendRepository.save(trend);

            log.info("트렌드 등록 : {} (count={}, rank={})",
                    stat.getKeyword().getValue(),
                    stat.getTotalCount(),
                    rank - 1);
        }

        log.info("[Trend] 트렌드 계산 완료");
    }
}
