package com.trendkeyword.trend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordCountServiceImpl implements KeywordCountService {

    private final StringRedisTemplate redisTemplate;
    /** Redis 키 (현재 윈도우 기준) */
    private static final String KEY = "trend:keyword:latest";

    /** 트렌드 유지 시간 */
    private static final Duration TTL = Duration.ofMinutes(30);

    /** 최소 키워드 길이 */
    private static final int MIN_KEYWORD_LENGTH = 2;

    // 키워드 카운트
    @Override
    public void countKeywords(List<String> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            log.warn("[KeywordCount] 입력 키워드가 비어 있습니다. count 작업 스킵");
            return;
        }

        int successCount = 0;
        int skippedCount = 0;

        for (String keyword : keywords) {

            // 🔒 입력 검증
            if (!isValidKeyword(keyword)) {
                skippedCount++;
                continue;
            }

            try {
                redisTemplate.opsForZSet()
                        .incrementScore(KEY, keyword, 1.0);
                log.info("keyword={}", keyword);
                successCount++;

            } catch (DataAccessException e) {
                log.error(
                        "[KeywordCount] Redis 카운트 실패 - keyword={}, error={}",
                        keyword, e.getMessage(), e
                );
            }
        }

        // TTL 설정 (매번 갱신)
        try {
            redisTemplate.expire(KEY, TTL);
        } catch (DataAccessException e) {
            log.error("[KeywordCount] TTL 설정 실패 - error={}", e.getMessage(), e);
        }

        log.info(
                "[KeywordCount] 카운트 완료 - total={}, success={}, skipped={}",
                keywords.size(), successCount, skippedCount
        );
    }

    // Top N 조회
    @Override
    public Map<String, Double> getTopKeywords(int limit) {
        if (limit <= 0) {
            log.warn("[KeywordCount] 잘못된 limit 값: {}", limit);
            return Collections.emptyMap();
        }

        Set<String> values;
        try {
            values = redisTemplate.opsForZSet()
                    .reverseRange(KEY, 0, limit - 1);
        } catch (DataAccessException e) {
            log.error("[KeywordCount] Redis 조회 실패 - error={}", e.getMessage(), e);
            return Collections.emptyMap();
        }

        if (values == null || values.isEmpty()) {
            log.info("[KeywordCount] 조회된 키워드 없음");
            return Collections.emptyMap();
        }

        Map<String, Double> result = new LinkedHashMap<>();

        for (String value : values) {
            try {
                Double score = redisTemplate.opsForZSet().score(KEY, value);
                if (score != null) {
                    result.put(value, score);
                }
            } catch (DataAccessException e) {
                log.error(
                        "[KeywordCount] score 조회 실패 - keyword={}, error={}",
                        value, e.getMessage(), e
                );
            }
        }

        log.info(
                "[KeywordCount] Top 키워드 조회 완료 - size={}",
                result.size()
        );

        return result;
    }

    // 카운트 초기화
    @Override
    public void clear() {
        try {
            redisTemplate.delete(KEY);
            log.info("[KeywordCount] Redis 키 초기화 완료 - key={}", KEY);
        } catch (DataAccessException e) {
            log.error(
                    "[KeywordCount] Redis 키 삭제 실패 - key={}, error={}",
                    KEY, e.getMessage(), e
            );
        }
    }

    // 내부 검증 로직
    private boolean isValidKeyword(String keyword) {

        if (keyword == null) {
            return false;
        }

        String trimmed = keyword.trim();

        if (trimmed.length() < MIN_KEYWORD_LENGTH) {
            return false;
        }

        // 숫자만 있는 경우 제거 (선택)
        if (trimmed.matches("\\d+")) {
            return false;
        }

        return true;
    }
}
