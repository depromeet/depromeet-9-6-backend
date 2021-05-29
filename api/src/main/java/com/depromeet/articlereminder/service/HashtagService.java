package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HashtagService {

//    Hashtag getHashtagByName(String name);

    Hashtag saveHashtag(String hashtagName);
}
