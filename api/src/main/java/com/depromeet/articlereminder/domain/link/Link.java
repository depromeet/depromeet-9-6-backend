package com.depromeet.articlereminder.domain.link;

import com.depromeet.articlereminder.domain.BaseEntity;
import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.dto.link.LinkRequest;
import com.depromeet.articlereminder.exception.LinkHasBeenAlreadyReadException;
import com.depromeet.articlereminder.exception.LinkModifiedByInvalidUserException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "linkURL", "status", "completedAt"})
public class Link extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id; // 링크 id

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 사용자

    @OneToMany(mappedBy = "link", cascade = CascadeType.ALL)
    private List<LinkHashtag> linkHashtags = new ArrayList<>();

    private String linkURL; // 링크 url

    @Enumerated(EnumType.STRING)
    private LinkStatus status; // 링크 읽음 상태

    private LocalDateTime completedAt; // 읽음 완료 일시

    /**
     * 링크 생성 메서드
     * @param member
     * @param linkURL
     * @param linkHashtags
     * @return
     */
    public static Link createLink(Member member, String linkURL, List<LinkHashtag> linkHashtags) {
        Link link = new Link();
        link.changeLinkURL(linkURL);
        link.changeMember(member);

        for (LinkHashtag hashtag : linkHashtags) {
            link.addLinkHashtag(hashtag);
        }
        link.changeInitialLinkStatus();

        return link;
    }

    /**
     * 링크 생성 메서드
     * @param member
     * @param linkURL
     * @param linkHashtags
     * @return
     */
    public Link createLink(Member member, String linkURL) {
        Link link = new Link();
        link.changeLinkURL(linkURL);
        link.changeMember(member);

        for (LinkHashtag hashtag : linkHashtags) {
            link.addLinkHashtag(hashtag);
        }
        link.changeInitialLinkStatus();

        return link;
    }

    /***
     * 링크 - 해시태그 추가
     * @param hashtag
     */
    public void addLinkHashtag(LinkHashtag hashtag) {
        linkHashtags.add(hashtag);
        hashtag.changeLink(this);
    }

    /**
     * 읽음 완료 메서드
     */
    public Link markRead(Long todayCount) {
        if (this.status == LinkStatus.READ) {
            throw new LinkHasBeenAlreadyReadException();
        }
        this.status = LinkStatus.READ;
        this.completedAt = LocalDateTime.now();
        this.member.changeTotalCount();

        int point = 100;

        // 5의 배수로 읽은 경우
        if (todayCount > 1 && todayCount % 5 == 1) {
            point += 20;
        }

        // TODO 7일 연속으로 읽은 경우
        if (todayCount == 0) {

        }

        this.member.changeTotalPoint(point);

        return this;
    }

    public Link update(Member member, String linkURL, List<LinkHashtag> linkHashtags) {
        this.isValidUser(member);

        this.changeLinkURL(linkURL);

        for (LinkHashtag linkHashtag : linkHashtags) {
            linkHashtag.deleteLinkHashTag();
            this.addLinkHashtag(linkHashtag);
        }

        return this;
    }

    /**
     * 링크 삭제
     * @param member
     */
    public Link deleteLink(Member member) {
        this.isValidUser(member);

        for (LinkHashtag linkHashtag : linkHashtags) {
            linkHashtag.deleteLinkHashTag();
        }

        linkHashtags.clear();

        return this;
    }

    private void changeMember(Member member) {
        this.member = member;
    }

    private void changeLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    private void changeInitialLinkStatus() {
        this.status = LinkStatus.UNREAD;
    }

    private void isValidUser(Member member) {
        if (this.member != null && !this.member.equals(member)) {
            throw new LinkModifiedByInvalidUserException("해당 링크에 접근 권한이 없는 사용자입니다.");
        }
    }

}
