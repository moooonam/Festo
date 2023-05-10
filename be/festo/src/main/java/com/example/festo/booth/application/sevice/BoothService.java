package com.example.festo.booth.application.sevice;

import com.example.festo.booth.adapter.in.web.model.FiestaResponse;
import com.example.festo.booth.application.port.in.ChangeBoothStatusUseCase;
import com.example.festo.booth.application.port.in.GetFiestaListUseCase;
import com.example.festo.booth.application.port.in.RegisterBoothCommand;
import com.example.festo.booth.application.port.in.RegisterBoothUseCase;
import com.example.festo.booth.application.port.out.*;
import com.example.festo.booth.domain.BoothStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoothService implements RegisterBoothUseCase, GetFiestaListUseCase, ChangeBoothStatusUseCase {
    private final SaveImgPort saveImgPort;
    private final SaveBoothPort saveBoothPort;
    private final LoadFiestaListPort loadFiestaListPort;
    private final LoadBoothStatusPort loadBoothStatusPort;
    @Override
    public Long registerBooth(RegisterBoothCommand registerBoothCommand) {
        SaveBoothCommand saveBoothCommand = SaveBoothCommand.builder()
                .boothName(registerBoothCommand.getBoothName())
                .description(registerBoothCommand.getDescription())
                .location(registerBoothCommand.getLocation())
                .openTime(registerBoothCommand.getOpenTime())
                .closeTime(registerBoothCommand.getCloseTime())
                .imageUrl("")
                .build();

        Long saveBoothId = saveBoothPort.saveBooth(saveBoothCommand, registerBoothCommand.getOwnerId(), registerBoothCommand.getFestivalId());

        String imgUrl = saveImgPort.saveBoothImg(registerBoothCommand.getImg(),saveBoothId);

        saveBoothId = saveBoothPort.updateSetImg(saveBoothId,imgUrl);

        return saveBoothId;
    }

    @Override
    public List<FiestaResponse.Owner> getFiestaListByOwner(Long ownerId) {
        return loadFiestaListPort.loadFiestaListByOwnerId(ownerId);
    }

    @Override
    public boolean changeBoothStatus(String status,Long boothId) {
        if(BoothStatus.valueOf(status) == loadBoothStatusPort.loadBoothStatus(boothId)){
            return false;
        }
        else{
            loadBoothStatusPort.setBoothStatus(BoothStatus.valueOf(status),boothId);
            return true;
        }
    }
}
