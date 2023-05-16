package com.example.festo.alert.application.service;

import com.example.festo.alert.application.port.in.SaveFcmDeviceTokenUseCase;
import com.example.festo.alert.application.port.out.LoadFcmDeviceTokenPort;
import com.example.festo.alert.domain.FcmDeviceToken;
import com.example.festo.member.application.port.out.SaveFcmDeviceTokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService implements SaveFcmDeviceTokenUseCase {

    private final SaveFcmDeviceTokenPort saveFcmDeviceTokenPort;

    private final LoadFcmDeviceTokenPort loadFcmDeviceTokenPort;

    @Override
    public void save(Long memberId, String token) {
        if (token == null) {
            return;
        }

        FcmDeviceToken fcmDeviceToken = loadFcmDeviceTokenPort.loadFcmDeviceTokenByMemberId(memberId);
        if (fcmDeviceToken == null) {
            saveFcmDeviceTokenPort.save(new FcmDeviceToken(memberId, token));
        }

        if (fcmDeviceToken.getToken().equals(token)) {
            return;
        }

        saveFcmDeviceTokenPort.save(new FcmDeviceToken(memberId, token));
    }
}
