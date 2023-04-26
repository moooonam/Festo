package com.example.festo.member.application;

import com.example.festo.global.auth.JwtUtil;
import com.example.festo.member.domain.Member;
import com.example.festo.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;

    public LoginResponse login(Long authId, String nickname, String profileUrl) {
        Member member = memberRepository.findByAuthId(authId)
                                        .orElse(Member.builder()
                                                      .authId(authId)
                                                      .nickname(nickname)
                                                      .profileImagePath(profileUrl)
                                                      .build());

        memberRepository.save(member);
        String accessToken = jwtUtil.createAccessToken(member.getId());
        String refreshToken = jwtUtil.createRefreshToken(member.getId());

        return new LoginResponse(accessToken, refreshToken);
    }
}
