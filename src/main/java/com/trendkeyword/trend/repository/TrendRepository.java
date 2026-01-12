package com.trendkeyword.trend.repository;

import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.Trend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TrendRepository extends JpaRepository<Trend, Long> {

    /**
     * 특정 시간대 트렌드 전체 조회
     */
    List<Trend> findAllByTimeWindow(LocalDateTime timeWindow);

    /**
     * 키워드 + 시간대 트렌드 단건 조회
     */
    Optional<Trend> findByKeywordAndTimeWindow(Keyword keyword, LocalDateTime timeWindow);

    /**
     * 특정 시간대 랭킹 순 조회
     */
    List<Trend> findAllByTimeWindowOrderByRankAsc(LocalDateTime timeWindow);

    boolean existsByKeywordAndTimeWindow(Keyword keyword, LocalDateTime timeWindow);
}
