package com.example.festo.festival.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "festival")
public class FestivalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
