package com.trendkeyword.trendkeyword.trend.domain;

import com.trendkeyword.trend.domain.Keyword;
import com.trendkeyword.trend.domain.KeywordSource;
import com.trendkeyword.trend.repository.KeywordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class KeywordRepositoryTest {

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    @DisplayName("keyword 저장 및 조회")
    void saveAndFind(){
        // given
        Keyword keyword = new Keyword("삼성", KeywordSource.NEWS);

        // when
        Keyword save =  keywordRepository.save(keyword);

        // then
        Assertions.assertThat(save.getId()).isNotNull();
        Assertions.assertThat(save.getValue()).isEqualTo("삼성");
        Assertions.assertThat(save.getSource()).isEqualTo(KeywordSource.NEWS);
        Assertions.assertThat(save.isActive()).isTrue();
    }

    @Test
    @DisplayName("value는 중복될 수 없다.")
    void existsValue(){
        keywordRepository.save(new Keyword("jpa", KeywordSource.BLOG));

        boolean exist = keywordRepository.existsByValue("sts");

        Assertions.assertThat(exist).isFalse();
    }

}
