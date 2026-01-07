package com.trendkeyword.collector.source;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NewsRssClientImpl implements NewsRssClient {

    private final RestClient restClient;

    @Override
    public String searchNews(String rssUrl) {
        return restClient.get()
                .uri(rssUrl)
                .retrieve()
                .body(String.class);
    }

}
