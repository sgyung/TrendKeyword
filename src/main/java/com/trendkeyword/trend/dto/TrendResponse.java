package com.trendkeyword.trend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrendResponse {

    private String keyword;
    private double growthRate;
    private double trendScore;
    private int rank;

//    private String sentiment;
}
