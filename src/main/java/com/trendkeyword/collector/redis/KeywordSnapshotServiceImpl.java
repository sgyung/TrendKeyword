package com.trendkeyword.collector.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordSnapshotServiceImpl implements KeywordSnapshotService {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Map<String, Integer> getSnapshot(String key) {

        Map<Object, Object> raw = redisTemplate.opsForHash().entries(key);

        return raw.entrySet().stream()
                .collect(Collectors.toMap(
                   e -> (String) e.getKey(),
                   e -> Integer.parseInt(e.getValue().toString())
                ));
    }

}
