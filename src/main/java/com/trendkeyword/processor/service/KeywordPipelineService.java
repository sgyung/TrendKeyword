package com.trendkeyword.processor.service;

import com.trendkeyword.collector.event.KeywordSnapshotEvent;

public interface KeywordPipelineService {

    void handle(KeywordSnapshotEvent event);
}
