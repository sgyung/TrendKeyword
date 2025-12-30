package com.trendkeyword.collector;

import com.trendkeyword.collector.rss.NewsRssClient;
import com.trendkeyword.collector.keyword.KeywordExtractor;
import com.trendkeyword.collector.rss.NewsRssSource;
import com.trendkeyword.collector.rss.RssParser;
import com.trendkeyword.trend.service.KeywordCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsCollector {

    private final NewsRssClient newsRssClient;
    private final RssParser rssParser;
    private final KeywordExtractor keywordExtractor;
    private final KeywordCountService keywordCountService;


    @Async
    public void collectLatestNews() {

        List<String> titles = new ArrayList<>();

        for (NewsRssSource source : NewsRssSource.values()) {
            String xml = newsRssClient.searchNews(source.getUrl());
            titles.addAll(rssParser.extractTitles(xml));
        }

        List<String> keywords = keywordExtractor.extractKeywords(titles);
        keywordCountService.countKeywords(keywords);
    }
}
