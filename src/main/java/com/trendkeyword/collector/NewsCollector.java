package com.trendkeyword.collector;

import com.trendkeyword.api.naver.NaverNewsApiClient;
import com.trendkeyword.collector.dto.NaverNewsItemDto;
import com.trendkeyword.collector.dto.NaverNewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsCollector {

    private final NaverNewsApiClient naverNewsApiClient;

    @Async
    public void collect(String query, int display, int start){
        NaverNewsResponseDto response = naverNewsApiClient.searchNews(query, start, display);

        for (NaverNewsItemDto item : response.getItems()) {

            String title = item.getTitle();

            // TODO 1. HTML 태그 제거
            // TODO 2. 키워드 추출 (processor)
            // TODO 3. Kafka 발행 or KeywordService 호출

            System.out.println("[NEWS TITLE] " + title);
        }
    }
}
