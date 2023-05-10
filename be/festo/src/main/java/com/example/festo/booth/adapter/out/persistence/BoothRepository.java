package com.example.festo.booth.adapter.out.persistence;

import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoothRepository extends JpaRepository<BoothEntity, Long> {
//    List<BoothEntity> findAllByOwnerId(Long ownerId);
    List<FestivalEntity> findAllByOwnerId(Long ownerId);
    @Query("SELECT DISTINCT b.festival FROM BoothEntity b WHERE b.owner.id = :ownerId")
    List<FestivalEntity> findDistinctFestivalsByOwnerId(Long ownerId);

}
