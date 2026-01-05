package com.trendkeyword.collector.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
public class KeywordSnapshotEvent {

    private LocalDateTime timeWindow;

    private String source;

    private Map<String, Integer> keywordCounts;

    public KeywordSnapshotEvent(LocalDateTime timeWindow, String source, Map<String, Integer> keywordCounts) {
        this.timeWindow = timeWindow;
        this.source = source;
        this.keywordCounts = keywordCounts;
    }
}
