package com.podo.climb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiFailedException extends RuntimeException {
    private int code;
    private String message;
}

