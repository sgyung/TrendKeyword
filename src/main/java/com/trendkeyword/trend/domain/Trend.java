package com.trendkeyword.trend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "trend",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_trend_keyword_time",
                        columnNames = {"keyword_id", "time_window"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_trend_time",
                        columnList = "time_window"
                ),
                @Index(
                        name = "idx_trend_keyword_time",
                        columnList = "keyword_id, time_window"
                )
        }
)
public class Trend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trend_id")
    private Long id;

    /**
     * 트렌드로 판단된 키워드
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "keyword_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_trend_keyword")
    )
    private Keyword keyword;

    /**
     * 트렌드 판단 기준 시간 구간
     */
    @Column(name = "time_window", nullable = false)
    private LocalDateTime timeWindow;

    /**
     * 증가율 (이전 구간 대비)
     */
    @Column(name = "growth_rate", nullable = false)
    private double growthRate;

    /**
     * 트렌드 점수 (가중치 포함)
     */
    @Column(name = "trend_score", nullable = false)
    private double trendScore;

    /**
     * 해당 시간대 트렌드 랭킹
     */
    @Column(name = "trend_rank", nullable = false)
    private int rank;

    /**
     * 트렌드 감지 시각
     */
    @Column(name = "detected_at", nullable = false, updatable = false)
    private LocalDateTime detectedAt;

    // =========================
    // 생성자
    // =========================

    public Trend(
            Keyword keyword,
            LocalDateTime timeWindow,
            double growthRate,
            double trendScore,
            int rank
    ) {
        if (keyword == null) {
            throw new IllegalArgumentException("keyword는 필수입니다.");
        }
        if (timeWindow == null) {
            throw new IllegalArgumentException("timeWindow는 필수입니다.");
        }

        this.keyword = keyword;
        this.timeWindow = timeWindow;
        this.growthRate = growthRate;
        this.trendScore = trendScore;
        this.rank = rank;
    }

    // =========================
    // JPA 라이프사이클
    // =========================

    @PrePersist
    protected void prePersist() {
        this.detectedAt = LocalDateTime.now();
    }
}
