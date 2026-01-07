package com.trendkeyword.collector.aggregate;

import java.util.Map;

public interface KeywordSnapshotService {

    Map<String, Integer> getSnapshot(String key);
}
