package com.trendkeyword.collector.keyword;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class KeywordExtractor {

    // 뉴스 키워드
    private static final Set<String> STOP_WORDS = Set.of(
            "경제","정치","사회","생활","IT","과학","세계","속보","날씨","스포츠"
    );

    private static final Pattern HTML_TAG = Pattern.compile("<[^>]*>");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("[^가-힣a-zA-Z0-9 ]");

    public static List<String> extractKeywords(List<String> titles){
        Map<String, Integer> frequencyMap = new HashMap<>();

        for(String title : titles){
            String cleaned = clean(title);
        }
    }

    private static String clean(String text) {
        String noHtml = HTML_TAG.matcher(text).replaceAll("");
        return SPECIAL_CHAR.matcher(noHtml).replaceAll("").trim();
    }
}
