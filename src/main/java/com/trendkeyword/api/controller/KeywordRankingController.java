package com.trendkeyword.api.controller;

import com.trendkeyword.collector.dto.KeywordRankDto;
import com.trendkeyword.trend.service.KeywordRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KeywordRankingController {

    private final KeywordRankingService keywordRankingService;

    @GetMapping("/trend/keywords")
    public List<KeywordRankDto> getTrendingKeywords(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return keywordRankingService.getTopKeywords(limit);
    }
}
