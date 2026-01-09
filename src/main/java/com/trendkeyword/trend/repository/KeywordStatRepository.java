package com.trendkeyword.trend.repository;

import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.KeywordStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KeywordStatRepository extends JpaRepository<KeywordStat, Long> {

    Optional<KeywordStat> findByKeywordAndTimeWindow(Keyword keyword, LocalDateTime timeWindow);
    List<KeywordStat> findAllByTimeWindow(LocalDateTime timeWindow);
}
