package com.example.festo.member.application.port.out;

import com.example.festo.member.domain.AuthId;
import com.example.festo.member.domain.Member;

public interface LoadMemberPort {

    Member loadMember(AuthId authId);
}
