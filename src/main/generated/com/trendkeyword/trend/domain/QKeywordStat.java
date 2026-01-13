package com.trendkeyword.trend.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QKeywordStat is a Querydsl query type for KeywordStat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QKeywordStat extends EntityPathBase<KeywordStat> {

    private static final long serialVersionUID = -1693328167L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QKeywordStat keywordStat = new QKeywordStat("keywordStat");

    public final NumberPath<Integer> blogCount = createNumber("blogCount", Integer.class);

    public final NumberPath<Integer> cafeCount = createNumber("cafeCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QKeyword keyword;

    public final NumberPath<Integer> newsCount = createNumber("newsCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> timeWindow = createDateTime("timeWindow", java.time.LocalDateTime.class);

    public final NumberPath<Integer> totalCount = createNumber("totalCount", Integer.class);

    public final NumberPath<Integer> youtubeCount = createNumber("youtubeCount", Integer.class);

    public QKeywordStat(String variable) {
        this(KeywordStat.class, forVariable(variable), INITS);
    }

    public QKeywordStat(Path<? extends KeywordStat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QKeywordStat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QKeywordStat(PathMetadata metadata, PathInits inits) {
        this(KeywordStat.class, metadata, inits);
    }

    public QKeywordStat(Class<? extends KeywordStat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new QKeyword(forProperty("keyword")) : null;
    }

}

