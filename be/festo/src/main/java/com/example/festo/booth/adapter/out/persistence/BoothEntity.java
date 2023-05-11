package com.example.festo.booth.adapter.out.persistence;

import com.example.festo.booth.domain.BoothStatus;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "booth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoothEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boothId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalTime openTime;
    @Column(nullable = false)
    private LocalTime closeTime;
    @Column(nullable = false)
    private String locationDescription;
    @Column(nullable = false)
    private String boothDescription;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BoothStatus boothStatus;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Member owner;

    @ManyToOne
    @JoinColumn(name = "festival_id")
    private FestivalEntity festival;

    @Builder
    public BoothEntity(Long boothId, String name, LocalTime openTime, LocalTime closeTime, String locationDescription, String boothDescription, String imageUrl, BoothStatus boothStatus, Member owner, FestivalEntity festival) {
        this.boothId = boothId;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.locationDescription = locationDescription;
        this.boothDescription = boothDescription;
        this.imageUrl = imageUrl;
        this.boothStatus = boothStatus;
        this.owner = owner;
        this.festival = festival;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setBoothStatus(BoothStatus boothStatus) {
        this.boothStatus = boothStatus;
    }
}
