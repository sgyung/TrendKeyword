package com.trendkeyword.collector.keyword;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KeywordExtractor {

    private final Komoran komoran;

    private static final Pattern HTML_TAG = Pattern.compile("<[^>]*>");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("[^가-힣a-zA-Z0-9 ]");

    public List<String> extractKeywords(List<String> texts){
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String text : texts) {

            String cleaned = clean(text);

            // 1️⃣ 형태소 분석
            List<Token> tokens =
                    komoran.analyze(cleaned).getTokenList();

            // 2️⃣ 명사만 추출
            List<String> nouns = tokens.stream()
                    .filter(this::isNoun)
                    .map(Token::getMorph)
                    .toList();

            // 3️⃣ 뉴스 1건당 중복 제거
            Set<String> uniqueKeywords = new HashSet<>();

            // 단일 명사
            uniqueKeywords.addAll(nouns);

            // 4️⃣ 명사 바이그램 생성
            for (int i = 0; i < nouns.size() - 1; i++) {
                String bigram = nouns.get(i) + " " + nouns.get(i + 1);
                uniqueKeywords.add(bigram);
            }

            // 5️⃣ 뉴스 단위로 카운트
            for (String keyword : uniqueKeywords) {
                frequencyMap.merge(keyword, 1, Integer::sum);
            }
        }

        // 6️⃣ 빈도순 정렬
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();
    }

    private String clean(String text) {
        String noHtml = HTML_TAG.matcher(text).replaceAll("");
        return SPECIAL_CHAR.matcher(noHtml).replaceAll("").trim();
    }

    private boolean isNoun(Token token) {
        return ("NNG".equals(token.getPos())
                || "NNP".equals(token.getPos()))
                && token.getMorph().length() >= 2;
    }
}
