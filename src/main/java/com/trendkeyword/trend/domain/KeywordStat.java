package com.trendkeyword.trend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "keyword_stat",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_keyword_stat_keyword_time",
                        columnNames = {"keyword_id", "time_window"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_keyword_stat_time",
                        columnList = "time_window"
                ),
                @Index(
                        name = "idx_keyword_stat_keyword_time",
                        columnList = "keyword_id, time_window"
                )
        }
)
public class KeywordStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_stat_id")
    private Long id;

    /**
     * 집계 대상 키워드
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "keyword_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_keyword_stat_keyword")
    )
    private Keyword keyword;

    /**
     * 30분 단위 집계 기준 시각
     */
    @Column(name = "time_window", nullable = false)
    private LocalDateTime timeWindow;

    /**
     * 출처별 카운트
     */
    @Column(name = "news_count", nullable = false)
    private int newsCount = 0;

    @Column(name = "blog_count", nullable = false)
    private int blogCount = 0;

    @Column(name = "cafe_count", nullable = false)
    private int cafeCount = 0;

    @Column(name = "youtube_count", nullable = false)
    private int youtubeCount = 0;

    /**
     * 전체 합계
     */
    @Column(name = "total_count", nullable = false)
    private int totalCount;

    /**
     * 실제 DB 저장 시각
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // =========================
    // 생성자
    // =========================

    public KeywordStat(Keyword keyword, LocalDateTime timeWindow) {
        if (keyword == null) {
            throw new IllegalArgumentException("keyword는 필수입니다.");
        }
        if (timeWindow == null) {
            throw new IllegalArgumentException("timeWindow는 필수입니다.");
        }

        this.keyword = keyword;
        this.timeWindow = timeWindow;
    }

    // =========================
    // JPA 라이프사이클
    // =========================

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        recalcTotal();
    }

    // =========================
    // 도메인 로직
    // =========================

    public void increaseBySource(KeywordSource source, int count) {
        if (count <= 0) {
            return;
        }

        switch (source) {
            case NEWS -> this.newsCount += count;
            case BLOG -> this.blogCount += count;
            case CAFE -> this.cafeCount += count;
            case YOUTUBE -> this.youtubeCount += count;
        }

        recalcTotal();
    }

    public void recalcTotal() {
        this.totalCount =
                newsCount +
                        blogCount +
                        cafeCount +
                        youtubeCount;
    }
}
