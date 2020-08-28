package com.podo.climb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

public enum BoardFilterType {
    REGISTER(1), LIKE(2), SOLVE(3), FOLLOW(4);
    private final int value;

    BoardFilterType(int value) {
        this.value = value;
    }

    public static BoardFilterType valueOf(Integer n) {
        return Arrays.stream(BoardFilterType.values())
                     .filter(t -> Objects.equals(t.value, n))
                     .findAny().orElse(null);
    }

    @JsonCreator
    public static BoardFilterType forJsonValue(String value) {
        return BoardFilterType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

    public Integer getValue() {
        return this.value;
    }
}
