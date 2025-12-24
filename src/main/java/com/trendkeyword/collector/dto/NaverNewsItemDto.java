package com.trendkeyword.collector.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverNewsItemDto {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private String pubDate;
}
