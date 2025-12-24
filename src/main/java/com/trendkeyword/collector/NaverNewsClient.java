package com.trendkeyword.collector;

import com.trendkeyword.api.naver.NaverNewsApiClient;
import com.trendkeyword.collector.dto.NaverNewsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NaverNewsClient implements NaverNewsApiClient {

    private final RestClient restClient;

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    @Override
    public NaverNewsResponseDto searchNews(String query,  int display, int start) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("openapi.naver.com")
                        .path("/v1/search/news.json")
                        .queryParam("query", query)
                        .queryParam("display",display)
                        .queryParam("start",start)
                        .queryParam("sort","date")
                        .build())
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .retrieve()
                .body(NaverNewsResponseDto.class);
    }

}
