package com.trendkeyword.trend.service;

import com.trendkeyword.trend.dto.TrendResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TrendQueryService {

    // 최신 트렌드 Top N
    public List<TrendResponse> getLatestTrends(int limit);

    // 특정 시점 트렌드
    public List<TrendResponse> getTrendsByTimeWindow(LocalDateTime timeWindow, int limit);

    // 기간 히스토리
    public List<TrendResponse> getTrendHistory(LocalDateTime from, LocalDateTime to);
}
