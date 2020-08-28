package com.podo.climb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

public enum BoardType {
    PROBLEM(1), COMMUNITY(2), NOTICE(3);
    private final int value;

    BoardType(int value) {
        this.value = value;
    }

    public static BoardType valueOf(Integer n) {
        return Arrays.stream(BoardType.values())
                     .filter(t -> Objects.equals(t.value, n))
                     .findAny().orElse(null);
    }

    @JsonCreator
    public static BoardType forJsonValue(String value) {
        return BoardType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

    public Integer getValue() {
        return this.value;
    }
}
