package com.example.festo.member.adapter.in.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberInfoResponse {

    private final String nickname;

    private final String profileImageUrl;

    public MemberInfoResponse(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
