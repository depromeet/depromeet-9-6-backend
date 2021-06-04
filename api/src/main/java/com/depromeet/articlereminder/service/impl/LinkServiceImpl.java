package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.depromeet.articlereminder.domain.link.LinkStatus;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.link.LinkRequest;
import com.depromeet.articlereminder.exception.HashtagNumberShouldNotBeMoreThanThree;
import com.depromeet.articlereminder.exception.LinkNotFoundException;
import com.depromeet.articlereminder.exception.UserNotFoundException;
import com.depromeet.articlereminder.repository.HashtagRepository;
import com.depromeet.articlereminder.repository.LinkRepository;
import com.depromeet.articlereminder.repository.MemberRepository;
import com.depromeet.articlereminder.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkServiceImpl implements LinkService {

    private final MemberRepository memberRepository;
    private final LinkRepository linkRepository;
    private final HashtagRepository hashtagRepository;

    /**
     * 모두 조회
     */
    @Override
    @Transactional
    public Page<Link> findAllByUserAndStatus(Long userId, String status, Pageable pageable) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // FIXME QueryDSL로 status 동적으로 처리하기
        String isCompleted = getLinkStatus(status);

        if (isCompleted.equalsIgnoreCase("READ")) {
            return linkRepository.findByMemberAndRead(member, LinkStatus.valueOf("READ"), pageable);
        } else if (isCompleted.equalsIgnoreCase("UNREAD")) {
            return linkRepository.findByMemberAndUnread(member, LinkStatus.valueOf("UNREAD"), pageable);
        }

        return linkRepository.findByMember(member, pageable);
    }

    /**
     * 새로운 링크 등록
     * @param userId
     * @param linkRequest
     * @return
     */
    @Override
    @Transactional
    public Link saveLink(Long userId, LinkRequest linkRequest) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if (linkRequest.getHashtags().size() > 3) {
            throw new HashtagNumberShouldNotBeMoreThanThree("해시태그는 3개까지만 설정 가능합니다.");
        }

        List<LinkHashtag> linkHashtagList = linkRequest.getHashtags()
                                    .stream()
                                    .map(hashtag -> LinkHashtag.createLinkHashTag(saveHashtag(hashtag)))
                                    .collect(Collectors.toList());

        Link link = Link.createLink(member, linkRequest.getLinkURL(), linkHashtagList);

        Link saved = linkRepository.save(link);

        return linkRepository.findById(saved.getId()).get(); // FIXME 코드 고치기

    }

    @Transactional
    public Hashtag saveHashtag(String current) {
        Optional<Hashtag> hashtag = hashtagRepository.findByName(current);

        if (hashtag.isPresent()) {
            return hashtag.get();
        } else {
            Hashtag created = Hashtag.createHashtag(current);
            hashtagRepository.save(created);
            return hashtagRepository.findByName(current).get();
        }
    }

    @Override
    public Link getLink(Long linkId) {
        return linkRepository.findById(linkId)
                            .orElseThrow(() -> new LinkNotFoundException(linkId));
    }

    @Override
    @Transactional
    public Link updateLink(Long userId, Long linkId, LinkRequest linkRequest) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Link link = linkRepository.findById(linkId)
                .map(l -> l.update(member, linkRequest))
                .orElseThrow(() -> new LinkNotFoundException(linkId));
        return linkRepository.save(link);
    }

    /**
     * 링크 삭제
     * @param userId
     * @param linkId
     */
    @Override
    @Transactional
    public void deleteLink(Long userId, Long linkId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Link link = linkRepository.findById(linkId).orElseThrow(() -> new LinkNotFoundException(linkId));

        link.deleteLink(member);
        linkRepository.deleteById(link.getId());
    }

    @Override
    @Transactional
    public Link markAsRead(Long userId, Long linkId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new LinkNotFoundException(linkId));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = today.format(formatter);

        Long currentCountOfDay = linkRepository.findCountOfReadToday(member, LinkStatus.READ ,formattedString);

        link.markRead(currentCountOfDay);
        return linkRepository.save(link);

        // TODO 포인트 지급
    }

    private String getLinkStatus(String completed) {
        if ("ALL".equals(completed)) {
            return "ALL";
        }
        return "T".equals(completed) ? "READ" : "UNREAD";
    }
}
