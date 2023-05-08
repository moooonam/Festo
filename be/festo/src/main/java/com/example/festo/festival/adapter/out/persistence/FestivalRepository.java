package com.example.festo.festival.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FestivalRepository extends JpaRepository<FestivalEntity,Long> {
    boolean existsByInviteCode(String inviteCode);
    List<MainFestivalProjection> findAllProjectedBy();

    List<SearchFestivalProjection> findByNameContaining(String keyword);
}
