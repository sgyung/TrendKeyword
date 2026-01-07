package com.trendkeyword.collector;

import com.trendkeyword.collector.producer.KeywordSnapshotProducer;
import com.trendkeyword.collector.source.NewsRssClient;
import com.trendkeyword.collector.extractor.KeywordExtractor;
import com.trendkeyword.collector.source.NewsRssSource;
import com.trendkeyword.collector.source.RssParser;
import com.trendkeyword.collector.aggregate.KeywordCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewsCollector {

    private final NewsRssClient newsRssClient;
    private final RssParser rssParser;
    private final KeywordExtractor keywordExtractor;
    private final KeywordCountService keywordCountService;
    private final KeywordSnapshotProducer snapshotProducer;


    @Async
    public void collectLatestNews() {

        List<String> titles = new ArrayList<>();

        for (NewsRssSource source : NewsRssSource.values()) {
            String xml = newsRssClient.searchNews(source.getUrl());
            titles.addAll(rssParser.extractTitles(xml));
        }

        List<String> keywords = keywordExtractor.extractKeywords(titles);
        keywordCountService.countKeywords(keywords);

        snapshotProducer.produce(
                "trend:keyword:latest",
                LocalDateTime.now(),
                "NEWS"
        );
    }
}
