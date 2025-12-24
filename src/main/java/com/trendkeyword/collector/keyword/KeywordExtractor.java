package com.trendkeyword.collector.keyword;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KeywordExtractor {

    // 뉴스 키워드
    private static final Set<String> STOP_WORDS = Set.of(
            "경제","정치","사회","생활","IT","과학","세계","속보","날씨","스포츠"
    );

    private static final Pattern HTML_TAG = Pattern.compile("<[^>]*>");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("[^가-힣a-zA-Z0-9 ]");

    public static List<String> extractKeywords(List<String> titles){
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String title : titles) {
            String cleaned = clean(title);

            for (String word : cleaned.split("\\s+")) {
                if (isValidKeyword(word)) {
                    frequencyMap.merge(word, 1, Integer::sum);
                }
            }
        }

        // 빈도수 기준 정렬
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static String clean(String text) {
        String noHtml = HTML_TAG.matcher(text).replaceAll("");
        return SPECIAL_CHAR.matcher(noHtml).replaceAll("").trim();
    }

    private static boolean isValidKeyword(String word) {
        return word.length() >= 2 && !STOP_WORDS.contains(word);
    }
}
