package com.example.festo.festival.application.port.in;

import com.example.festo.festival.adapter.in.web.model.FestivalResponse;

import java.util.List;

public interface GetFestivalsUseCase {
    List<FestivalResponse.MainPage> getFestivalByMain();
    List<FestivalResponse.Search> getFestivalBySearch(String keyword);

    List<FestivalResponse.Manager> getFestivalByManager(Long managerId);
}
