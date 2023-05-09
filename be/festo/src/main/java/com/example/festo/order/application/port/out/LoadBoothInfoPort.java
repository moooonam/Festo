package com.example.festo.order.application.port.out;

import com.example.festo.order.domain.BoothInfo;

public interface LoadBoothInfoPort {
    BoothInfo loadBoothInfo(Long boothId);
}
