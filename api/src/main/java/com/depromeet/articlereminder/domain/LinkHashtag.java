package com.depromeet.articlereminder.domain;

import com.depromeet.articlereminder.domain.hashtag.Hashtag;
import com.depromeet.articlereminder.domain.link.Link;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkHashtag { // contains 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_hashtag_id")
    private Long id; // 링크-해시태그 id

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "link_id")
    private Link link; // 링크

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag; // 해시태그

    @CreatedDate
    private LocalDateTime createdAt; // 생성 일시

    public void setLink(Link link) {
        this.link = link;
    }
}