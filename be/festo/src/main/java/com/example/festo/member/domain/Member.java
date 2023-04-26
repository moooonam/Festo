package com.example.festo.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Embedded
    private AuthId authId;

    @Embedded
    private ProfileImageUrl profileImageUrl;

    @Builder
    public Member(AuthId authId, String nickname, ProfileImageUrl profileImageUrl) {
        this.authId = authId;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
