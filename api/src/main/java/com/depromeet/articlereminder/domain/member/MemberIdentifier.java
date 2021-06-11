package com.depromeet.articlereminder.domain.member;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@ToString(of = {"id", "loginId", "appleUserIdentifier"})
public class MemberIdentifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loginId; // 임시 loginId

    private String appleUserIdentifier; // apple Token
}
