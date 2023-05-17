package com.example.festo.alert.adapter.out.persistence;

import com.example.festo.alert.application.port.out.LoadFcmDeviceTokenPort;
import com.example.festo.alert.domain.FcmDeviceToken;
import com.example.festo.member.application.port.out.SaveFcmDeviceTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FcmDeviceTokenAdapter implements SaveFcmDeviceTokenPort, LoadFcmDeviceTokenPort {

    private final FcmDeviceTokenRepository fcmDeviceTokenRepository;

    @Override
    public void save(FcmDeviceToken fcmDeviceToken) {
        fcmDeviceTokenRepository.save(new FcmDeviceTokenEntity(fcmDeviceToken.getId(), fcmDeviceToken.getMemberId(), fcmDeviceToken.getToken()));
    }

    @Override
    public FcmDeviceToken loadFcmDeviceTokenByMemberId(Long memberId) {
        FcmDeviceTokenEntity fcmDeviceTokenEntity = fcmDeviceTokenRepository.findByMemberId(memberId).orElse(null);

        if (fcmDeviceTokenEntity == null) {
            return null;
        }

        return new FcmDeviceToken(fcmDeviceTokenEntity.getId(), memberId, fcmDeviceTokenEntity.getToken());
    }
}
