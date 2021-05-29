package com.depromeet.articlereminder.domain;

import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id"})
public class LinkHashtag extends BaseEntity { // contains 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "link_hashtag_id")
    private Long id; // 링크-해시태그 id

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "link_id")
    private Link link; // 링크

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag; // 해시태그

    public static LinkHashtag createLinkHashTag(Hashtag hashtag) {
        LinkHashtag linkHashtag = new LinkHashtag();
        linkHashtag.attachHashtag(hashtag);

        return linkHashtag;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    private void attachHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

    /**
     * 해시태그 - 링크 연관 관계 삭제
     */
    public void remove() {

    }
}
