package com.example.festo.order.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.booth.adapter.out.persistence.BoothRepository;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.order.application.port.out.LoadFestivalInfoPort;
import com.example.festo.order.domain.FestivalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FestivalInfoPersistenceAdapter implements LoadFestivalInfoPort {

    private final BoothRepository boothRepository;

    @Override
    public FestivalInfo loadFestivalInfoByBoothId(Long boothId) {
        BoothEntity boothEntity = boothRepository.findById(boothId)
                                                 .orElseThrow(NoSuchElementException::new);
        FestivalEntity festivalEntity = boothEntity.getFestival();

        return mapToFestivalInfoDomain(festivalEntity);
    }

    private FestivalInfo mapToFestivalInfoDomain(FestivalEntity festivalEntity) {
        return new FestivalInfo(festivalEntity.getId(), festivalEntity.getFestivalName(), festivalEntity.getImageUrl());
    }
}
