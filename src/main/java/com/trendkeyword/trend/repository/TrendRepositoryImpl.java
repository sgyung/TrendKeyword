package com.trendkeyword.trend.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.trendkeyword.trend.domain.QTrend;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TrendRepositoryImpl implements TrendRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public LocalDateTime findLatestTimeWindow(){
        QTrend qTrend = QTrend.trend;

        return queryFactory
                .select(qTrend.timeWindow.max())
                .from(qTrend)
                .fetchOne();
    }
}
