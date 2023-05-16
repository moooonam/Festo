package com.example.festo.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;
@Getter
@AllArgsConstructor
public class CustomNoSuchException extends NoSuchElementException {
    private final ErrorCode errorCode;
}
