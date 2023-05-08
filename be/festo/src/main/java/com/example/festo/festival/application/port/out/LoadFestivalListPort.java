package com.example.festo.festival.application.port.out;

import com.example.festo.festival.adapter.in.web.model.FestivalResponse;

import java.util.List;

public interface LoadFestivalListPort {
    List<FestivalResponse.MainPage> findAllFestivals();
    List<FestivalResponse.Search> findAllFestivalsBySearch(String keyword);
}
