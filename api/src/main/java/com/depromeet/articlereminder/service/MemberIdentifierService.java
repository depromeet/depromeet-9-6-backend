package com.depromeet.articlereminder.service;

import com.depromeet.articlereminder.domain.member.MemberIdentifier;

public interface MemberIdentifierService {

    MemberIdentifier getLoginIdByUserIdentifier(String userIdentifier);
}
