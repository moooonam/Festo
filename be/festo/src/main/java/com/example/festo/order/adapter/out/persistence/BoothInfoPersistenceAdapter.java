package com.example.festo.order.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.booth.adapter.out.persistence.BoothRepository;
import com.example.festo.order.application.port.out.LoadBoothInfoPort;
import com.example.festo.order.domain.BoothInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class BoothInfoPersistenceAdapter implements LoadBoothInfoPort {

    private final BoothRepository boothRepository;

    @Override
    public BoothInfo loadBoothInfo(Long boothId) {
        BoothEntity boothEntity = boothRepository.findById(boothId)
                                                 .orElseThrow(NoSuchElementException::new);
        return mapToBoothInfoDomain(boothEntity);
    }

    private BoothInfo mapToBoothInfoDomain(BoothEntity boothEntity) {
        return new BoothInfo(boothEntity.getBoothId(), boothEntity.getOwner().getId(), boothEntity.getName());
    }
}
