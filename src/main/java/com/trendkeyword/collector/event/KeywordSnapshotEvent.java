package com.trendkeyword.collector.event;

import com.trendkeyword.trend.domain.KeywordSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class KeywordSnapshotEvent {

    private String timeWindow;

    private KeywordSource source;

    private Map<String, Integer> keywordCounts;

    public KeywordSnapshotEvent(String timeWindow, KeywordSource source, Map<String, Integer> keywordCounts) {
        this.timeWindow = timeWindow;
        this.source = source;
        this.keywordCounts = keywordCounts;
    }
}
