package com.example.festo.booth.adapter.out.persistence;

import com.example.festo.booth.domain.BoothStatus;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.member.domain.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "booth")
@Getter
@Builder
public class BoothEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boothId;
    private String name;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String locationDescription;
    private String boothDescription;
    private String imageUrl;
    private String category;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BoothStatus boothStatus;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Member owner;

    @ManyToOne
    @JoinColumn(name = "festival_id")
    private FestivalEntity festival;


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
