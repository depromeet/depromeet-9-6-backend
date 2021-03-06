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
import com.depromeet.articlereminder.repository.LinkHashtagRepository;
import com.depromeet.articlereminder.repository.LinkRepository;
import com.depromeet.articlereminder.repository.MemberRepository;
import com.depromeet.articlereminder.service.LinkService;
import com.depromeet.articlereminder.service.MemberBadgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final LinkHashtagRepository linkHashtagRepository;
    private final MemberBadgeService memberBadgeService;

    @Override
    @Transactional
    public Page<Link> findAllByUserAndStatus(Long userId, String status, Pageable pageable) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // FIXME QueryDSL??? status ???????????? ????????????
        String isCompleted = getLinkStatus(status);

        if (isCompleted.equalsIgnoreCase("READ")) {
            return linkRepository.findByMemberAndRead(member, pageable);
        } else if (isCompleted.equalsIgnoreCase("UNREAD")) {
            return linkRepository.findByMemberAndUnread(member, pageable);
        }

        return linkRepository.findByMember(member, pageable);
    }

    @Override
    @Transactional
    public Link saveLink(Long userId, LinkRequest linkRequest) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        checkHashtagsSize(linkRequest);

        List<LinkHashtag> linkHashtagList = linkRequest.getHashtags()
                                    .stream()
                                    .map(hashtag -> saveLinkHashTag(saveHashtag(hashtag)))
                                    .collect(Collectors.toList());

        Link link = Link.createLink(member, linkRequest.getLinkURL(), linkHashtagList);

        Link saved = linkRepository.save(link);

        return linkRepository.findById(saved.getId()).get(); // FIXME ?????? ?????????

    }

    private LinkHashtag saveLinkHashTag(Hashtag hashtag) {
        LinkHashtag created = LinkHashtag.createLinkHashTag(hashtag);
        linkHashtagRepository.save(created);
        return linkHashtagRepository.findById(created.getId()).get();
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

        checkHashtagsSize(linkRequest);

        Link link = linkRepository.findById(linkId).orElseThrow(() -> new LinkNotFoundException(linkId));

        // FIXME ?????? -> ?????? ?????? ?????????
        List<LinkHashtag> linkHashtags = linkHashtagRepository.findAllByLink(link);

        for (LinkHashtag linkHashtag : linkHashtags) {
            linkHashtagRepository.delete(linkHashtag);
        }
        link.deleteLink(member);

        List<LinkHashtag> linkHashtagList = linkRequest.getHashtags()
                .stream()
                .map(hashtag -> saveLinkHashTag(saveHashtag(hashtag)))
                .collect(Collectors.toList());

        link.update(member, linkRequest.getLinkURL(),linkHashtagList);

        Link saved = linkRepository.save(link);

        return linkRepository.findById(saved.getId()).get();
    }

    private void checkHashtagsSize(LinkRequest linkRequest) {
        if (linkRequest.getHashtags().size() > 3) {
            throw new HashtagNumberShouldNotBeMoreThanThree();
        }
    }

    @Override
    @Transactional
    public void deleteLink(Long userId, Long linkId) {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        Link link = linkRepository.findById(linkId).orElseThrow(() -> new LinkNotFoundException(linkId));

        List<LinkHashtag> linkHashtags = linkHashtagRepository.findAllByLink(link);

        for (LinkHashtag linkHashtag : linkHashtags) {
            linkHashtagRepository.delete(linkHashtag);
        }

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

        Long currentCountOfDay = linkRepository.findReadCountOfToday(member, LinkStatus.READ ,formattedString);

        int origin = member.getTotalPoint();
        link.markRead(currentCountOfDay);

        memberBadgeService.changeMemberPointBadge(member, origin ,link.getMember().getTotalPoint());

        return linkRepository.save(link);
    }

    private String getLinkStatus(String completed) {
        if ("ALL".equals(completed)) {
            return "ALL";
        }
        return "T".equals(completed) ? "READ" : "UNREAD";
    }

    @Transactional
    public void deleteUserLinks(Member member) {
        List<Link> userLinks = linkRepository.findByMemberId(member);

        userLinks
                .forEach(link -> {
                    deleteLinkHashtagsAndLink(link, member);
                });
    }

    @Transactional
    public void deleteLinkHashtagsAndLink(Link link, Member member) {
        List<LinkHashtag> linkHashtags = linkHashtagRepository.findAllByLink(link);

        for (LinkHashtag linkHashtag : linkHashtags) {
            linkHashtagRepository.delete(linkHashtag);
        }

        link.deleteLink(member);
        linkRepository.deleteById(link.getId());
    }

    @Override
    public Long getReadCountOfToday(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedString = today.format(formatter);

        return linkRepository.findReadCountOfToday(member, LinkStatus.READ, formattedString);
    }

    @Override
    public Long getReadCountOfSeason(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String formattedString = today.format(formatter);

        return linkRepository.findReadCountOfSeason(member, LinkStatus.READ, formattedString);
    }

    @Override
    public List<Link> getLinksByUserId(Member member) {
        return linkRepository.findByMemberId(member);
    }
}
