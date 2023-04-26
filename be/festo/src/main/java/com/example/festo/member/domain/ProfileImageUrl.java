package com.example.festo.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProfileImageUrl {

    @Column(nullable = false, name = "profile_image_url")
    private String value;

    protected ProfileImageUrl() {
    }

    public ProfileImageUrl(String value) {
        this.value = value;
    }

    public static ProfileImageUrl of(String profileUrl) {
        return new ProfileImageUrl(profileUrl);
    }

    public String getValue() {
        return this.value;
    }
}
