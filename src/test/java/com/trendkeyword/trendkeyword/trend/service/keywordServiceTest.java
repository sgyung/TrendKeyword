package com.trendkeyword.trendkeyword.trend.service;

import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.KeywordSource;
import com.trendkeyword.trend.repository.KeywordRepository;
import com.trendkeyword.trend.service.KeywordServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class keywordServiceTest {

    @Mock
    private KeywordRepository keywordRepository;

    @InjectMocks
    private KeywordServiceImpl keywordService;

    @Test
    void duplicateKeyword() {
        Mockito.when(keywordRepository.existsByValue("spring")).thenReturn(true);

        Assertions.assertThatThrownBy(()->
                keywordService.registerKeyword("spring", KeywordSource.BLOG)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void existsKeyword() {
        // given
        Long keywordId = 1L;
        Mockito.when(keywordRepository.findById(keywordId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(()->
                keywordService.deactivateKeyword(keywordId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("키워드를 찾을 수 없습니다.");
    }

    @Test
    void deactivateKeyword() {
        Keyword keyword = new Keyword("spring", KeywordSource.NEWS);

        Mockito.when(keywordRepository.findById(1L)).thenReturn(Optional.of(keyword));

        keywordService.deactivateKeyword(1L);

        Assertions.assertThat(keyword.isActive()).isFalse();
    }
}
