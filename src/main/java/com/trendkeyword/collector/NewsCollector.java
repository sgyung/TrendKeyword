package com.trendkeyword.collector;

import com.trendkeyword.api.naver.NaverNewsApiClient;
import com.trendkeyword.collector.dto.NaverNewsResponseDto;
import com.trendkeyword.collector.keyword.KeywordExtractor;
import com.trendkeyword.trend.service.KeywordCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsCollector {

    private final NaverNewsApiClient naverNewsApiClient;
    private final KeywordExtractor keywordExtractor;
    private final KeywordCountService keywordCountService;

    @Async
    public void collectLatestNews() {

        List<String> titles = new ArrayList<>();

        for (String query : NewsCollectionCategory.DEFAULT) {
            NaverNewsResponseDto response =
                    naverNewsApiClient.searchNews(query, 50, 1);

            response.getItems().forEach(item ->
                    titles.add(item.getTitle() + " " + item.getDescription()));
        }

        // 👉 여기서 KeywordExtractor 호출
        List<String> keywords =
                keywordExtractor.extractKeywords(titles);

        // 🔥 Redis 카운트
        keywordCountService.countKeywords(keywords);
    }
}
