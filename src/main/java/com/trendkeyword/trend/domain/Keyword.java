package com.trendkeyword.trend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "keyword",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_keyword_value",
                        columnNames = "value"
                )
        }
)
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    @Column(name = "keyword_value", nullable = false, length = 100)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private KeywordSource source;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_dt", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    // 생성자
    public Keyword(String value, KeywordSource source) {
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException("keyword value는 필수입니다.");
        }
        if(source == null){
            throw new IllegalArgumentException("keyword source는 필수입니다.");
        }

        this.value = value;
        this.source = source;
        this.active = true;
    }

    // JPA 라이프사이클

    @PrePersist
    protected void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    // 도메인 메서드

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }
}
