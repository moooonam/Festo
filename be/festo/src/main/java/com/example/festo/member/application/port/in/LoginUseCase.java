package com.example.festo.member.application.port.in;

import com.example.festo.member.adapter.in.web.model.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(Long authId, String nickname, String profileImageUrl);
}
