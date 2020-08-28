package com.podo.climb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

public enum MemberRoleType {
    ADMIN(1), MEMBER(2);
    private final int value;

    MemberRoleType(int value) {
        this.value = value;
    }

    public static MemberRoleType valueOf(Integer n) {
        return Arrays.stream(MemberRoleType.values())
                     .filter(t -> Objects.equals(t.value, n))
                     .findAny().orElse(null);
    }

    @JsonCreator
    public static MemberRoleType forJsonValue(String value) {
        return MemberRoleType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

    public Integer getValue() {
        return this.value;
    }
}
