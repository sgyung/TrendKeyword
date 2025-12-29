package com.trendkeyword.trend.service;

import java.util.List;
import java.util.Map;

public interface KeywordCountService {

    // 키워드 등장 횟수 누적
    void countKeywords(List<String> keywords);

    //Top N 키워드 조회
    Map<String, Double> getTopKeywords(int limit);

    // 카운트 초기화 (윈도우 종료 시)
    void clear();
}
