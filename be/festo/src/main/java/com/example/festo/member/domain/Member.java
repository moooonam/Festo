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

    @Column(nullable = false, unique = true)
    private Long authId;

    @Column(nullable = false)
    private String profileImagePath;

    @Builder
    public Member(Long authId, String nickname, String profileImagePath) {
        this.authId = authId;
        this.nickname = nickname;
        this.profileImagePath = profileImagePath;
    }
}
