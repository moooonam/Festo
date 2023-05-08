package com.example.festo.festival.adapter.out.persistence;

import com.example.festo.festival.domain.FestivalStatus;
import com.example.festo.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "festival")
@Getter
@Builder
public class FestivalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
