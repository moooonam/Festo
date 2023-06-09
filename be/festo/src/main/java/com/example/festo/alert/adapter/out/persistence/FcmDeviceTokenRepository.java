package com.example.festo.alert.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcmDeviceTokenRepository extends JpaRepository<FcmDeviceTokenEntity, Long> {
    Optional<FcmDeviceTokenEntity> findByToken(String token);
    List<FcmDeviceTokenEntity> findAllByMemberId(Long memberId);

    Optional<FcmDeviceTokenEntity> findByMemberId(Long memberId);
}
