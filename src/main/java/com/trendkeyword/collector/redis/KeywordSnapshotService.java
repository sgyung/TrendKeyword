package com.trendkeyword.collector.redis;

import java.util.Map;

public interface KeywordSnapshotService {

    Map<String, Integer> getSnapshot(String key);
}
