package com.trendkeyword.trend.dto;

import com.trendkeyword.trend.domain.Keyword;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrendCandidate {

    private Keyword keyword;
    private double growthRate;
    private double trendScore;

}
