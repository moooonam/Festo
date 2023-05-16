package com.example.festo.alert.adapter.out.persistence;

import com.example.festo.alert.application.port.out.LoadFcmDeviceTokenPort;
import com.example.festo.alert.domain.FcmDeviceToken;
import com.example.festo.member.application.port.out.SaveFcmDeviceTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FcmDeviceTokenAdapter implements SaveFcmDeviceTokenPort, LoadFcmDeviceTokenPort {

    private final FcmDeviceTokenRepository fcmDeviceTokenRepository;

    @Override
    public void save(FcmDeviceToken fcmDeviceToken) {
        fcmDeviceTokenRepository.save(new FcmDeviceTokenEntity(fcmDeviceToken.getMemberId(), fcmDeviceToken.getToken()));
    }

    @Override
    public FcmDeviceToken loadFcmDeviceTokenByToken(String token) {
        Optional<FcmDeviceTokenEntity> fcmDeviceTokenEntity = fcmDeviceTokenRepository.findByToken(token);

        if (fcmDeviceTokenEntity.isPresent()) {
            FcmDeviceTokenEntity fcmDeviceToken = fcmDeviceTokenEntity.get();
            return new FcmDeviceToken(fcmDeviceToken.getMemberId(), fcmDeviceToken.getToken());
        }

        return null;
    }

    @Override
    public FcmDeviceToken loadFcmDeviceTokenByMemberId(Long memberId) {
        FcmDeviceTokenEntity fcmDeviceTokenEntity = fcmDeviceTokenRepository.findByMemberId(memberId).orElse(null);

        if (fcmDeviceTokenEntity == null) {
            return null;
        }

        return new FcmDeviceToken(memberId, fcmDeviceTokenEntity.getToken());
    }
}
