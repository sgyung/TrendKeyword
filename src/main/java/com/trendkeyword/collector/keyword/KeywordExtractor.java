package com.trendkeyword.collector.keyword;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class KeywordExtractor {

    private final Komoran komoran;

    private static final Pattern HTML_TAG = Pattern.compile("<[^>]*>");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("[^가-힣a-zA-Z0-9 ]");

    public List<String> extractKeywords(List<String> titles){
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String title : titles) {
            String cleaned = clean(title);

            List<Token> tokens =
                    komoran.analyze(cleaned).getTokenList();

            for (Token token : tokens) {
                if (isNoun(token)) {
                    frequencyMap.merge(
                            token.getMorph(),
                            1,
                            Integer::sum
                    );
                }
            }
        }

        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
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
