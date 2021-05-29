package com.depromeet.articlereminder.repository;

import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class HashtagRepositoryTest {

    @Autowired
    HashtagRepository hashtagRepository;

    @Test
    @DisplayName("해시태그명으로 찾는 테스트")
    void findByHashtagName() throws Exception {
        // given
        Hashtag hashtag1 = new Hashtag("디자인");
        Hashtag hashtag2 = new Hashtag("UI/UX");
        Hashtag hashtag3 = new Hashtag("스타트업");
        hashtagRepository.save(hashtag1);
        hashtagRepository.save(hashtag2);
        hashtagRepository.save(hashtag3);

        // when
        Hashtag findHashtag = hashtagRepository.findByName("2/UX").get();
        System.out.println("findHashtag " + findHashtag);

        // then
//        assertThat(findHashtag.getName()).isEqualTo(hashtag1.getName());
    }
}