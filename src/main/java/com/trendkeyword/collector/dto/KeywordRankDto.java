package com.trendkeyword.collector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordRankDto {

    private int rank;
    private String keyword;
    private double score;
}
