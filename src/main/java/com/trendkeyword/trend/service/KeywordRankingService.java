package com.trendkeyword.trend.service;

import com.trendkeyword.collector.dto.KeywordRankDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeywordRankingService {

    private static final String KEY = "trend:keyword:latest";

    private final RedisTemplate<String, String> redisTemplate;

    public List<KeywordRankDto> getTopKeywords(int limit) {

        Set<ZSetOperations.TypedTuple<String>> tuples =
                redisTemplate.opsForZSet()
                        .reverseRangeWithScores(KEY, 0, limit - 1);

        List<KeywordRankDto> result = new ArrayList<>();

        if (tuples == null) {
            return result;
        }

        int rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            result.add(
                    new KeywordRankDto(
                            rank++,
                            tuple.getValue(),
                            tuple.getScore()
                    )
            );
        }

        return result;
    }
}
