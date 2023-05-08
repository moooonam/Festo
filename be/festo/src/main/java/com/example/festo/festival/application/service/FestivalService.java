package com.example.festo.festival.application.service;


import com.example.festo.festival.adapter.in.web.model.FestivalResponse;

import com.example.festo.festival.application.port.in.GetFestivalsUseCase;
import com.example.festo.festival.application.port.in.RegisterFestivalCommand;
import com.example.festo.festival.application.port.in.RegisterFestivalUseCase;
import com.example.festo.festival.application.port.out.LoadFestivalListPort;
import com.example.festo.festival.application.port.out.SaveFestivalCommand;
import com.example.festo.festival.application.port.out.SaveFestivalPort;
import com.example.festo.festival.application.port.out.SaveImgPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalService implements RegisterFestivalUseCase, GetFestivalsUseCase {
    private final SaveImgPort saveImgPort;
    private final SaveFestivalPort saveFestivalPort;
    private final LoadFestivalListPort loadFestivalListPort;


    @Override
    public Long registerFestival(RegisterFestivalCommand registerFestivalCommand) {
        SaveFestivalCommand saveFestivalCommand = SaveFestivalCommand.builder()
                .festivalName(registerFestivalCommand.getFestivalName())
                .description(registerFestivalCommand.getDescription())
                .location(registerFestivalCommand.getLocation())
                .startDate(registerFestivalCommand.getStartDate())
                .endDate(registerFestivalCommand.getEndDate())
                .imageUrl("")
                .build();

        Long saveFestivalId = saveFestivalPort.saveFestival(saveFestivalCommand,registerFestivalCommand.getManagerId());
        String imgUrl = saveImgPort.saveFestivalImg(registerFestivalCommand.getImg(),saveFestivalId);

        saveFestivalId = saveFestivalPort.updateSetImg(saveFestivalId,imgUrl);
        return saveFestivalId;
    }

    @Override
    public List<FestivalResponse.MainPage> getFestivalByMain() {
        return loadFestivalListPort.findAllFestivals();
    }

    @Override
    public List<FestivalResponse.Search> getFestivalBySearch(String keyword) {
        return loadFestivalListPort.findAllFestivalsBySearch(keyword);
    }
}
