package com.trendkeyword.processor.service;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;

public interface KeywordSnapshotProcessService {

    void process(KeywordSnapshotEvent event);
}
