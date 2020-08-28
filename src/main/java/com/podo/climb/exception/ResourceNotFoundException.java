package com.podo.climb.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

public class ResourceNotFoundException extends ApiFailedException {

    public ResourceNotFoundException() {
        super(404, "Resource not found");
    }
}

