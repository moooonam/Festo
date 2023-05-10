package com.example.festo.booth.application.port.in;

public interface ChangeBoothStatusUseCase {
    boolean changeBoothStatus(String status,Long boothId);
}
