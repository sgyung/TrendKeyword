package com.trendkeyword.trend.service;

import com.trendkeyword.trend.domain.KeywordStat;
import com.trendkeyword.trend.domain.Trend;
import com.trendkeyword.trend.dto.TrendCandidate;
import com.trendkeyword.trend.repository.KeywordStatRepository;
import com.trendkeyword.trend.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TrendServiceImpl implements TrendService {

    private final KeywordStatRepository keywordStatRepository;
    private final TrendRepository trendRepository;

    /** 트렌드 최소 기준 */
    private static final int MIN_COUNT = 10;

    /** 저장할 트렌드 개수 */
    private static final int TREND_LIMIT = 20;

    @Override
    public void calculateTrends(LocalDateTime timeWindow) {
        log.info("[Trend] 트렌드 계산 시작 - {}", timeWindow);

        // 1. 현재 구간 통계 조회
        List<KeywordStat> currentStats =
                keywordStatRepository.findAllByTimeWindow(timeWindow);

        if (currentStats.isEmpty()) {
            log.info("[Trend] 대상 통계 없음 - 종료");
            return;
        }

        // 2. 이전 구간 계산
        LocalDateTime prevWindow = timeWindow.minusMinutes(30);

        List<TrendCandidate> candidates = new ArrayList<>();

        for (KeywordStat stat : currentStats) {

            // 2-1. 최소 기준 필터
            if (stat.getTotalCount() < MIN_COUNT) {
                continue;
            }

            // 2-2. 이전 통계 조회
            Optional<KeywordStat> prevStatOpt =
                    keywordStatRepository.findByKeywordAndTimeWindow(
                            stat.getKeyword(), prevWindow
                    );

            int prevCount =
                    prevStatOpt.map(KeywordStat::getTotalCount).orElse(0);

            int currCount = stat.getTotalCount();

            // 2-3. 증가율 계산
            double growthRate;
            if (prevCount == 0) {
                // 신규 급등 키워드
                growthRate = currCount;
            } else {
                growthRate = (double) (currCount - prevCount) / prevCount;
            }

            // 2-4. 트렌드 점수 계산
            double trendScore = calculateTrendScore(currCount, growthRate);

            candidates.add(new TrendCandidate(
                    stat.getKeyword(),
                    growthRate,
                    trendScore
            ));
        }

        if (candidates.isEmpty()) {
            log.info("[Trend] 트렌드 후보 없음 - 종료");
            return;
        }

        // 3. 점수 기준 정렬
        candidates.sort(
                Comparator.comparing(TrendCandidate::getTrendScore).reversed()
        );

        // 4. 상위 N개만 선택
        List<TrendCandidate> topCandidates =
                candidates.stream()
                        .limit(TREND_LIMIT)
                        .collect(Collectors.toList());

        int rank = 1;

        // 5. Trend 저장
        for (TrendCandidate c : topCandidates) {

            // 중복 방지
            if (trendRepository.existsByKeywordAndTimeWindow(
                    c.getKeyword(), timeWindow)) {
                log.info("이미 존재하는 트렌드 스킵 : {}", c.getKeyword().getValue());
                continue;
            }

            Trend trend = new Trend(
                    c.getKeyword(),
                    timeWindow,
                    c.getGrowthRate(),
                    c.getTrendScore(),
                    rank++
            );

            trendRepository.save(trend);

            log.info("트렌드 등록 : {} (score={}, growth={}, rank={})",
                    c.getKeyword().getValue(),
                    String.format("%.2f", c.getTrendScore()),
                    String.format("%.2f", c.getGrowthRate()),
                    rank - 1);
        }

        log.info("[Trend] 트렌드 계산 완료");
    }

    // =========================
    // 점수 계산 로직
    // =========================

    /**
     * 트렌드 점수 공식
     * - 절대량 60%
     * - 증가율 40%
     */
    private double calculateTrendScore(int totalCount, double growthRate) {
        double volumeScore = totalCount * 0.6;
        double growthScore = growthRate * 100 * 0.4;
        return volumeScore + growthScore;
    }
}
