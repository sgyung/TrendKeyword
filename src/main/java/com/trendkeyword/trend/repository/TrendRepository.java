package com.trendkeyword.trend.repository;

import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.Trend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TrendRepository extends JpaRepository<Trend, Long>, TrendRepositoryCustom {

    /**
     * 특정 시점의 트렌드 Top N 조회
     */
    List<Trend> findByTimeWindowOrderByRankAsc(
            LocalDateTime timeWindow,
            Pageable pageable
    );

    /**
     * 특정 시점의 전체 트렌드 조회
     */
    List<Trend> findAllByTimeWindowOrderByRankAsc(
            LocalDateTime timeWindow
    );

    /**
     * 기간별 트렌드 히스토리 조회
     */
    List<Trend> findAllByTimeWindowBetweenOrderByTimeWindowAsc(
            LocalDateTime from,
            LocalDateTime to
    );

    boolean existsByKeywordAndTimeWindow(Keyword keyword, LocalDateTime timeWindow);
}
