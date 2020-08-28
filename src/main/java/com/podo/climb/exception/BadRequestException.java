package com.podo.climb.exception;

public class BadRequestException extends ApiFailedException {

    public BadRequestException() {
        super(400, "Bad Request");
    }
}

