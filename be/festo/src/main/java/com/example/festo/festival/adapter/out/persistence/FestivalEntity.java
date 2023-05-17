package com.example.festo.festival.adapter.out.persistence;

import com.example.festo.festival.domain.FestivalStatus;
import com.example.festo.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "festival")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long festivalId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    private String imageUrl;
    @Column(unique = true, nullable = false)
    private String inviteCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
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
