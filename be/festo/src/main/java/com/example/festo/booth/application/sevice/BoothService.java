package com.example.festo.booth.application.sevice;

import com.example.festo.booth.application.port.in.RegisterBoothCommand;
import com.example.festo.booth.application.port.in.RegisterBoothUseCase;
import com.example.festo.booth.application.port.out.SaveBoothCommand;
import com.example.festo.booth.application.port.out.SaveBoothPort;
import com.example.festo.booth.application.port.out.SaveImgPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoothService implements RegisterBoothUseCase {
    private final SaveImgPort saveImgPort;
    private final SaveBoothPort saveBoothPort;
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
}
