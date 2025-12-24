package com.trendkeyword.collector.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class NaverNewsResponseDto {

    private int total;
    private int start;
    private int display;
    private List<NaverNewsItemDto> items;

}
