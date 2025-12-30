package com.trendkeyword.collector.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class RssParser {
    public List<String> extractTitles(String xml) {
        try {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new StringReader(xml));

            List<String> titles = new ArrayList<>();
            for (SyndEntry entry : feed.getEntries()) {
                titles.add(entry.getTitle());
            }
            return titles;

        } catch (Exception e) {
            throw new IllegalStateException("RSS 파싱 실패", e);
        }
    }
}
