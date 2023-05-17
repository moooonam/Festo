package com.example.festo.booth.application.port.out;

import com.example.festo.booth.domain.BoothStatus;

public interface LoadBoothStatusPort {
    BoothStatus loadBoothStatus(Long boothId);
    void setBoothStatus(BoothStatus boothStatus,Long boothId);
}
