package com.trendkeyword.trend.service;

import java.time.LocalDateTime;

public interface TrendService {

    void calculateTrends(LocalDateTime timeWindow);

}
