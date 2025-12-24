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

            System.out.println("[NEWS TITLE] " + title);
        }
    }
}
