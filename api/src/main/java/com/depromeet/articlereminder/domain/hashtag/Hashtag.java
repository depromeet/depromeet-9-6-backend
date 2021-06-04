package com.depromeet.articlereminder.domain.hashtag;

import com.depromeet.articlereminder.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id; // 해시태그 id

    private String name; // 해시태그명

    public Hashtag(String name) {
        this.name = name;
    }

    public static Hashtag from(String hashtagValue) {
        Hashtag hashtag = new Hashtag();
        hashtag.name = hashtagValue;
        return hashtag;
    }

}
