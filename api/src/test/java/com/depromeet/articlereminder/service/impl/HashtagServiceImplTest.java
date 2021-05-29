package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.repository.HashtagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class HashtagServiceImplTest {

    @Autowired
    HashtagServiceImpl hashtagService;

    @Autowired
    HashtagRepository hashtagRepository;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("해시태그 검색 테스트")
    void saveHashtag() throws Exception {
        // given
        Hashtag hashtag1 = new Hashtag("디자인");
        Hashtag hashtag2 = new Hashtag("스타트업");
        hashtagRepository.save(hashtag1);
        hashtagRepository.save(hashtag2);

        em.flush();
        em.clear();

        // when
        String hashtagName = "UI/UX";

        Optional<Hashtag> findHashtag = hashtagRepository.findByName(hashtagName);

        if (findHashtag.isPresent()) {
            System.out.println(hashtagRepository.findByName(hashtagName).get().getName());
        } else {
            Hashtag hashtag = Hashtag.from(hashtagName);
            Hashtag savedHashtag = hashtagRepository.save(hashtag);
            System.out.println(savedHashtag.getName());
        }

//        System.out.println(hashtagByName);


//        Hashtag findHashtag = hashtagService.saveHashtag(hashtagName);

        // then
//        assertThat(findHashtag.getName()).isEqualTo(hashtagName);

    }
}