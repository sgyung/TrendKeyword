package com.trendkeyword.collector.keyword;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class KeywordExtractor {

    private final Komoran komoran;

    private static final Pattern HTML_TAG = Pattern.compile("<[^>]*>");
    private static final Pattern SPECIAL_CHAR =
            Pattern.compile("[^가-힣a-zA-Z0-9 ]");

    // ✅ 뉴스 기준 확장 불용어
    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
            // 보도 관용어
            "기자","특파원","보도","속보","단독","종합","인터뷰","취재",
            "사진","영상","자료","제공","연합뉴스","뉴스",

            // 시점 / 기간
            "오늘","이번","최근","현재","올해","내년","연내","당시",
            "이후","이전","그간","현재까지","지금",

            // 상황 / 추상
            "상황","사실","문제","논란","의문","이유","배경",
            "가능성","전망","분석","관계","입장","의견","평가",
            "기대","우려","논의","관심","주목",

            // 행정 / 형식
            "관련","해당","대상","부분","경우","가운데","통해",
            "방안","계획","방침","대책","조치","대응","추진",
            "강화","확대","확인","검토","논의","결정","발표",

            // 정도 / 범위
            "모두","일부","다수","전체","최대","최소","이상","이하",
            "정도","수준","가량","약간",

            // 일반 명사 (의미 약함)
            "사람","시민","국민","주민","관계자","당사자",
            "이용","사용","운영","관리","지원","참여","활동",
            "제공","요청","설명","발언","주장","요구"
    ));

    public List<String> extractKeywords(List<String> titles) {

        List<String> result = new ArrayList<>();

        for (String title : titles) {

            if (!isValidText(title)) continue;

            String cleaned = clean(title);

            try {
                List<Token> tokens = komoran.analyze(cleaned).getTokenList();

                for (Token token : tokens) {

                    if (!isNoun(token)) continue;

                    String noun = normalizeNoun(token.getMorph());

                    if (!isValidNoun(noun)) continue;

                    result.add(noun); // ✅ 중복 허용
                }

            } catch (Exception ignored) {}
        }

        return result;
    }

    // ---------------- 내부 메서드 ----------------

    private String clean(String text) {
        String noHtml = HTML_TAG.matcher(text).replaceAll("");
        return SPECIAL_CHAR.matcher(noHtml).replaceAll(" ").trim();
    }

    /**
     * 명사만 (NNG, NNP)
     */
    private boolean isNoun(Token token) {
        return ("NNG".equals(token.getPos()) || "NNP".equals(token.getPos()))
                && token.getMorph().length() >= 2;
    }

    /**
     * 최소한의 조사 제거
     */
    private String normalizeNoun(String morph) {
        return morph.replaceAll(
                "(은|는|이|가|을|를|에|의|와|과|으로|로|에서|에게|께|만)$",
                ""
        );
    }

    /**
     * 명사 유효성 판단
     */
    private boolean isValidNoun(String word) {
        if (word.length() < 2) return false;
        if (STOPWORDS.contains(word)) return false;
        if (word.matches("\\d+")) return false;
        return true;
    }

    private boolean isValidText(String text) {
        return text != null && text.matches(".*[가-힣a-zA-Z].*");

    }
}
