package com.trendkeyword.api.controller;

import com.trendkeyword.collector.NewsCollector;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsCollectorController {

    private final NewsCollector newsCollector;

    @GetMapping("/news/collect")
    public String collectNews(@RequestParam String query,
                              @RequestParam(defaultValue = "10") int display,
                              @RequestParam(defaultValue = "1") int start) {

        newsCollector.collect(query, start, display);
        return "뉴스 수집 요청 완료(비동기)";
    }
}
