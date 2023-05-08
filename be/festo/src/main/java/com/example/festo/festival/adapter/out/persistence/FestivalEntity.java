package com.example.festo.festival.adapter.out.persistence;

import com.example.festo.festival.domain.FestivalStatus;
import com.example.festo.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "festival")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long festivalId;
    private String name;
    private String description;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
    @Column(unique = true, nullable = false)
    private String inviteCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FestivalStatus festivalStatus;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Member manager;

    @Builder
    public FestivalEntity(Long festivalId, String name, String description, String address, LocalDate startDate, LocalDate endDate, String imageUrl, String inviteCode, FestivalStatus festivalStatus, Member manager) {
        this.festivalId = festivalId;
        this.name = name;
        this.description = description;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.imageUrl = imageUrl;
        this.inviteCode = inviteCode;
        this.festivalStatus = festivalStatus;
        this.manager = manager;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
