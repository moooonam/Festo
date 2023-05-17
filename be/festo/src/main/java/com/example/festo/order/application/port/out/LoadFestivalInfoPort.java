package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.FestivalInfo;

public interface LoadFestivalInfoPort {

    FestivalInfo loadFestivalInfoByBoothId(Long boothId);
}
