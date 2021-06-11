package com.depromeet.articlereminder.domain.member;

import com.depromeet.articlereminder.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "loginId", "appleUserIdentifier"})
public class MemberIdentifier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "member_identifier_id")
    private Long id;

//    @Column(unique = true)
    private Long loginId; // 임시 loginId

    private String appleUserIdentifier; // apple Token

    public static MemberIdentifier createMemberLoginId(String appleUserIdentifier) {
        MemberIdentifier mid = new MemberIdentifier();
        mid.changeLoginId(getRandomNumber());
        mid.changeAppleUserIdentifier(appleUserIdentifier);
        return mid;
    }

    private void changeLoginId(Long loginId) {
        this.loginId = loginId;
    }

    private void changeAppleUserIdentifier(String appleUserIdentifier) {
        this.appleUserIdentifier = appleUserIdentifier;
    }

    private static Long getRandomNumber() {
        long leftLimit = 2000000000L;
        long rightLimit = 10000000000L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }
}
