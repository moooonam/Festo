package com.example.festo.member.adapter.in.web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private Long authId;

    private String nickname;

    private String profileImageUrl;

    @Builder
    public LoginRequest(Long authId, String nickname, String profileImageUrl) {
        this.authId = authId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
