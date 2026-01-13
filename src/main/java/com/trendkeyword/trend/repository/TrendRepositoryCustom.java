package com.trendkeyword.trend.repository;

import java.time.LocalDateTime;

public interface TrendRepositoryCustom {

    LocalDateTime findLatestTimeWindow();

}
