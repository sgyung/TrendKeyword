package com.trendkeyword.collector;

import java.util.Set;

public final class NewsCollectionCategory {

    private NewsCollectionCategory() {

    }

    public static final Set<String> DEFAULT = Set.of(
            "경제", "정치", "사회", "생활", "IT",
            "과학", "세계", "속보", "날씨", "스포츠"
    );

}
