package com.example.festo.member.application.port.out;

import com.example.festo.member.domain.Member;

public interface JoinMemberPort {

    Long join(Member member);
}
