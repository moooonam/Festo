package com.example.festo.festival.application.port.out;

public interface SaveFestivalPort {
    Long saveFestival(SaveFestivalCommand saveFestivalCommand,Long managerId);
    Long updateSetImg(Long festivalId,String imgUrl);
}
