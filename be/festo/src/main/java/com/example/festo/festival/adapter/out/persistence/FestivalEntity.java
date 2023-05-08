package com.example.festo.festival.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "festival")
@Getter
public class FestivalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private String festivalName;
}
