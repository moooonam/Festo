package com.example.festo.booth.adapter.out.persistence;

import com.example.festo.booth.application.port.out.SaveBoothCommand;
import com.example.festo.booth.application.port.out.SaveBoothPort;
import com.example.festo.booth.domain.BoothStatus;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.festival.adapter.out.persistence.FestivalRepository;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BoothPersistenceAdapter implements SaveBoothPort {
    //리포지토리 가져오기
    private final BoothRepository boothRepository;
    private final MemberRepository memberRepository;
    private final FestivalRepository festivalRepository;

    @Override
    public Long saveBooth(SaveBoothCommand saveBoothCommand, Long ownerId, Long festivalId) {
        Member owner = memberRepository.findById(ownerId).orElseThrow(NoSuchElementException::new);
        FestivalEntity festival = festivalRepository.findById(festivalId).orElseThrow(NoSuchElementException::new);

        BoothEntity boothEntity = BoothEntity.builder()
                .name(saveBoothCommand.getBoothName())
                .openTime(saveBoothCommand.getOpenTime())
                .closeTime(saveBoothCommand.getCloseTime())
                .locationDescription(saveBoothCommand.getLocation())
                .boothDescription(saveBoothCommand.getDescription())
                .imageUrl(saveBoothCommand.getImageUrl())
//                .category()
                .boothStatus(BoothStatus.CLOSE)
                .owner(owner)
                .festival(festival)
                .build();

        BoothEntity loadBoothEntity = boothRepository.save(boothEntity);

        return loadBoothEntity.getBoothId();
    }

    @Override
    public Long updateSetImg(Long boothId, String imgUrl) {
        BoothEntity boothEntity = boothRepository.findById(boothId).orElseThrow(NoSuchElementException::new);
        boothEntity.setImageUrl(imgUrl);
        boothRepository.save(boothEntity);
        return boothEntity.getBoothId();
    }
}
