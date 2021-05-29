package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.repository.HashtagRepository;
import com.depromeet.articlereminder.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private HashtagRepository hashtagRepository;

//    @Override
//    public Hashtag getHashtagByName(String name) {
//        Hashtag saved = hashtagRepository.findByName(name)
//                .orElseGet(() -> hashtagRepository.save(Hashtag.from(name)));
//        return saved;
//    }

    @Override
    @Transactional
    public Hashtag saveHashtag(String hashtagName) {
        // 해당 해시태그가 이미 존재하는지 검증
        Optional<Hashtag> findHashtag = hashtagRepository.findByName(hashtagName);

        if (findHashtag.isPresent()) {
            return hashtagRepository.findByName(hashtagName).get();
        }

        Hashtag newHashtag = Hashtag.from(hashtagName);
        return hashtagRepository.save(newHashtag);
    }

}
