package com.example.festo.festival.application.port.out;

import com.example.festo.festival.domain.Festival;

public interface LoadFestivalDetailPort {
    Festival loadFestivalDetailByFestivalId(Long festivalId);
}
