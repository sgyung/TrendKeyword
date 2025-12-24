package com.trendkeyword.trend.service;

import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.KeywordSource;

public interface KeywordService {

    Keyword registerKeyword(String value, KeywordSource keywordSource);

    void deactivateKeyword(Long keywordId);

}
