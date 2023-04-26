package com.example.festo.member.adapter.out.persistence;

import com.example.festo.member.application.port.out.JoinMemberPort;
import com.example.festo.member.application.port.out.LoadMemberPort;
import com.example.festo.member.domain.AuthId;
import com.example.festo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class MemberPersistenceAdapter implements LoadMemberPort, JoinMemberPort {

    private final MemberRepository memberRepository;

    @Override
    public Member loadMember(AuthId authId) {
        return memberRepository.findByAuthId(authId)
                               .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Long join(Member member) {
        return memberRepository.save(member)
                               .getId();
    }
}
