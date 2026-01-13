package com.trendkeyword.trend.service;

import com.trendkeyword.sentiment.service.SentimentService;
import com.trendkeyword.trend.domain.Trend;
import com.trendkeyword.trend.dto.TrendResponse;
import com.trendkeyword.trend.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TrendQueryServiceImpl implements TrendQueryService {

    private final TrendRepository trendRepository;
    private final SentimentService sentimentService;

    // 최신 트렌드 Top N
    @Override
    public List<TrendResponse> getLatestTrends(int limit) {

        // 1. 가장 최근 timeWindow 찾기
        LocalDateTime latestTimeWindow = trendRepository.findLatestTimeWindow();

        if(latestTimeWindow == null){
            new IllegalStateException("트렌드 데이터가 없습니다.");
        }

        log.info("[INFO] 최신 트렌드 조회 - timeWindow={}", latestTimeWindow);

        // 2. 해당 시점 Top N 조회
        Pageable pageable = PageRequest.of(0, limit);
        List<Trend> trends = trendRepository
                .findByTimeWindowOrderByRankAsc(latestTimeWindow, pageable);

        // 3. Entity → DTO 변환
        return trends.stream()
                .map(trend -> new TrendResponse(
                        trend.getKeyword().getValue(),
                        trend.getGrowthRate(),
                        trend.getTrendScore(),
                        trend.getRank()
                ))
                .toList();
    }

    @Override
    public List<TrendResponse> getTrendsByTimeWindow(LocalDateTime timeWindow, int limit) {
        return List.of();
    }

    @Override
    public List<TrendResponse> getTrendHistory(LocalDateTime from, LocalDateTime to) {
        return List.of();
    }
}
