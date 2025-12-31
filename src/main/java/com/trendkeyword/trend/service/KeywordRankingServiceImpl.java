package com.trendkeyword.trend.service;

import com.trendkeyword.collector.dto.KeywordRankDto;

import java.util.List;

public interface KeywordRankingServiceImpl {

    List<KeywordRankDto> getTopKeywords(int limit);

}
