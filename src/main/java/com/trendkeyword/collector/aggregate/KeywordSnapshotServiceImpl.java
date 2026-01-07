package com.trendkeyword.collector.aggregate;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordSnapshotServiceImpl implements KeywordSnapshotService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Map<String, Integer> getSnapshot(String key) {

        // ZSET 전체 조회
        Set<String> values =
                redisTemplate.opsForZSet().range(key, 0, -1);

        if (values == null || values.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> result = new HashMap<>();

        for (String value : values) {
            Double score =
                    redisTemplate.opsForZSet().score(key, value);

            if (score != null) {
                result.put(value, score.intValue());
            }
        }

        return result;
    }

}
