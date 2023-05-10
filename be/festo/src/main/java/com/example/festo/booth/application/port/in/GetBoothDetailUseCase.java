package com.example.festo.booth.application.port.in;

import com.example.festo.booth.domain.Booth;

public interface GetBoothDetailUseCase {
    Booth getBoothDetail(Long boothId);

}
