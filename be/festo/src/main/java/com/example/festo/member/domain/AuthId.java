package com.example.festo.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AuthId {

    @Column(nullable = false, unique = true, name = "auth_id")
    private Long value;

    protected AuthId() {
    }

    public AuthId(Long value) {
        this.value = value;
    }

    public boolean match(Long authId) {
        return this.value.equals(authId);
    }

    public static AuthId of(Long id) {
        return new AuthId(id);
    }
}
