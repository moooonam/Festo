package com.example.festo.booth.adapter.out.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "booth")
public class BoothEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boothId;

    public Long getId() {
        return null;
    }

    public Long getOwnerId() {
        return null;
    }
}
