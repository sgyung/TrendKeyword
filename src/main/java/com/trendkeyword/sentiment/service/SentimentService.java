package com.trendkeyword.sentiment.service;

import java.time.LocalDateTime;

public interface SentimentService {
    void analyzeSentiment(LocalDateTime timeWindow);
}
