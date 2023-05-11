package com.example.festo.member.adapter.in.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "[Request] authId는 빈 값일 수 없습니다.")
    private Long authId;

    @NotBlank(message = "[Request] nickname는 빈 값일 수 없습니다.")

    private String nickname;

    @NotBlank(message = "[Request] profileImageUrl는 빈 값일 수 없습니다.")
    private String profileImageUrl;

    @Builder
    public LoginRequest(Long authId, String nickname, String profileImageUrl) {
        this.authId = authId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
