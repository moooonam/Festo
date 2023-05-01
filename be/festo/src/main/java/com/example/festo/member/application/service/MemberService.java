package com.example.festo.member.application.service;

import com.example.festo.member.adapter.in.web.model.MemberInfoResponse;
import com.example.festo.member.application.port.in.MemberInfoUseCase;
import com.example.festo.member.application.port.out.LoadMemberPort;
import com.example.festo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberInfoUseCase {

    private final LoadMemberPort loadMemberPort;

    @Override
    public MemberInfoResponse loadMemberInfo(Long memberId) {
        Member member = loadMemberPort.loadMember(memberId);
        return new MemberInfoResponse(member.getNickname(), member.getProfileImageUrl().getValue());
    }
}
