package com.example.festo.booth.application.port.out;

import com.example.festo.member.domain.Member;

public interface SaveBoothPort {
    Long saveBooth(SaveBoothCommand saveBoothCommand,Long ownerId,Long festivalId);
    Long updateSetImg(Long boothId,String imgUrl);
}
