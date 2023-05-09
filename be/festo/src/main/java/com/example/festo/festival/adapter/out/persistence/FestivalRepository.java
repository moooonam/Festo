package com.example.festo.festival.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<FestivalEntity,Long> {
    boolean existsByInviteCode(String inviteCode);
    List<MainFestivalProjection> findAllProjectedBy();

    List<SearchFestivalProjection> findByNameContaining(String keyword);

    Optional<FestivalEntity> findByInviteCode(String inviteCode);

}
