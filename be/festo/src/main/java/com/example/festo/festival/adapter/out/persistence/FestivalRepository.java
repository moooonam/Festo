package com.example.festo.festival.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalRepository extends JpaRepository<FestivalEntity,Long> {
    boolean existsByInviteCode(String inviteCode);
}
