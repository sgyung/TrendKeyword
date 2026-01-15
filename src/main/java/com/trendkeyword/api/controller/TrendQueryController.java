package com.trendkeyword.api.controller;

import com.trendkeyword.trend.dto.TrendResponse;
import com.trendkeyword.trend.service.TrendQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/trends")
@RequiredArgsConstructor
public class TrendQueryController {

    private final TrendQueryService trendQueryService;

    @GetMapping("/latest")
    public List<TrendResponse> getLatest(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return trendQueryService.getLatestTrends(limit);
    }


     // 특정 시점 트렌드 Top N 조회
     // GET /api/trends?timeWindow=2026-01-15T10:30:00&limit=10
    @GetMapping
    public List<TrendResponse> getTrendsByTimeWindow(
            @RequestParam("timeWindow")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime timeWindow,

            @RequestParam(defaultValue = "10") int limit
    ) {
        return trendQueryService.getTrendsByTimeWindow(timeWindow, limit);
    }

    // 기간별 트렌드 히스토리 조회
    //GET /api/trends/history?from=2026-01-15T09:00:00&to=2026-01-15T12:00:00
    @GetMapping("/history")
    public List<TrendResponse> getTrendHistory(
            @RequestParam("from")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,

            @RequestParam("to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to
    ) {
        return trendQueryService.getTrendHistory(from, to);
    }

}
