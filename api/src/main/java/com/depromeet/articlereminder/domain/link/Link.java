package com.depromeet.articlereminder.domain.link;

import com.depromeet.articlereminder.domain.BaseEntity;
import com.depromeet.articlereminder.domain.LinkHashtag;
import com.depromeet.articlereminder.domain.member.Member;
import com.depromeet.articlereminder.exception.LinkModifiedByInvalidUserException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id; // 링크 id

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 사용자

    @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<LinkHashtag> linkHashtags = new ArrayList<>();

    private String linkURL; // 링크 url

    @Enumerated(value = EnumType.STRING)
    private LinkStatus status; // 링크 읽음 상태

    private LocalDateTime completedAt; // 읽음 완료 일시


    public static Link createLink(Member member, LinkHashtag... linkHashtags) {
        Link link = new Link();
        link.setMember(member);

        for (LinkHashtag hashtag : linkHashtags) {
            link.addLinkHashtag(hashtag);
        }

        return link;
    }

    /***
     * 링크 - 해시태그 추가
     * @param hashtag
     */
    public void addLinkHashtag(LinkHashtag hashtag) {
        linkHashtags.add(hashtag);
        hashtag.setLink(this);
    }

    /**
     * 읽음 완료 메서드
     */
    public void markRead() {
        if (this.status == LinkStatus.READ) {
            throw new IllegalStateException("link has been already read");
        }
        this.status = LinkStatus.READ;
        this.completedAt = LocalDateTime.now();
    }

    public void delete(Member member) {
        this.isValidUser(member);
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void isValidUser(Member member) {
        if (this.member != null && !this.member.equals(member)) {
            throw new LinkModifiedByInvalidUserException();
        }
    }

}
