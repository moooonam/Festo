package com.example.festo.member.application.port.in;

import com.example.festo.member.adapter.in.web.model.MemberInfoResponse;

public interface MemberInfoUseCase {

    MemberInfoResponse loadMemberInfo(Long memberId);
}
