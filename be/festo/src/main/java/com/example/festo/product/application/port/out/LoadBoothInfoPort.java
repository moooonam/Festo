package com.example.festo.product.application.port.out;

import com.example.festo.product.domain.BoothInfo;

public interface LoadBoothInfoPort {
    BoothInfo loadBoothInfo(Long boothId);
}
