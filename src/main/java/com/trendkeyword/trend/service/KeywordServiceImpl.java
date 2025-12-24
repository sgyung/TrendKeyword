package com.trendkeyword.trend.service;

import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.KeywordSource;
import com.trendkeyword.trend.repository.KeywordRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Server
@RequiredArgsConstructor
@Transactional
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;


    @Override
    public Keyword registerKeyword(String value, KeywordSource keywordSource) {
        if(keywordRepository.existsByValue(value)) {
            throw new IllegalStateException("이미 등록된 키워드입니다.");
        }

        Keyword keyword = new Keyword(value, keywordSource);

        return keywordRepository.save(keyword);
    }

    @Override
    public void deactivateKeyword(Long keywordId) {
        Keyword keyword = keywordRepository.findById(keywordId)
                .orElseThrow(() -> new IllegalArgumentException("키워드를 찾을 수 없습니다."));
        keyword.deactivate();
    }
}
