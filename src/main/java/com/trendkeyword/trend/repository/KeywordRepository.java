package com.trendkeyword.trend.repository;

import com.trendkeyword.trend.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByValue(String value);

    boolean existsByValue(String value);
}
