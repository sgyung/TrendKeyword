package com.trendkeyword.api.naver;

import com.trendkeyword.collector.dto.NaverNewsResponseDto;

public interface NaverNewsApiClient {

    NaverNewsResponseDto searchNews(String query, int display, int start);
}
