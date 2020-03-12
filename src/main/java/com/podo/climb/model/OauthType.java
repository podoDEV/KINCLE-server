package com.podo.climb.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Objects;

public enum OauthType {
    SELF(1), GOOGLE(2), APPLE(3);
    private int value;

    OauthType(int value) {
        this.value = value;
    }

    public static OauthType valueOf(Integer n) {
        return Arrays.stream(OauthType.values())
                     .filter(t -> Objects.equals(t.value, n))
                     .findAny().orElse(null);
    }

    @JsonCreator
    public static OauthType forJsonValue(String value) {
        return OauthType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

    public Integer getValue() {
        return this.value;
    }
}
