package com.depromeet.articlereminder.service.impl;

import com.depromeet.articlereminder.domain.member.MemberIdentifier;
import com.depromeet.articlereminder.repository.MemberIdentifierRepository;
import com.depromeet.articlereminder.service.MemberIdentifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberIdentifierServiceImpl implements MemberIdentifierService {

    private final MemberIdentifierRepository memberIdentifierRepository;

    @Override
    @Transactional
    public MemberIdentifier getLoginIdByUserIdentifier(String appleUserIdentifier) {
        MemberIdentifier find = memberIdentifierRepository.findByAppleUserIdentifier(appleUserIdentifier)
                .orElse(null);

        if (find != null) {
            return find;
        }

        MemberIdentifier created = MemberIdentifier.createMemberLoginId(appleUserIdentifier);

        MemberIdentifier saved = memberIdentifierRepository.save(created);

        return saved;
    }
}
