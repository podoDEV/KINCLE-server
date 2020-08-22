package com.podo.climb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Getter
@Setter
@AllArgsConstructor
public class ApiFailedException extends RuntimeException implements Supplier<RuntimeException> {
    private int code;
    private String message;

    @Override
    public RuntimeException get() {
        return this;
    }
}

