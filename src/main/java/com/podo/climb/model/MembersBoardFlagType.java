package com.podo.climb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

public enum MembersBoardFlagType {
    LIKE(1), DISLIKE(2), SOLVED(3), UNSOLVED(4), FOLLOW(5), UNFOLLOW(6);
    private final int value;

    MembersBoardFlagType(int value) {
        this.value = value;
    }

    public static MembersBoardFlagType valueOf(Integer n) {
        return Arrays.stream(MembersBoardFlagType.values())
                     .filter(t -> Objects.equals(t.value, n))
                     .findAny().orElse(null);
    }

    @JsonCreator
    public static MembersBoardFlagType forJsonValue(String value) {
        return MembersBoardFlagType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

    public Integer getValue() {
        return this.value;
    }
}
