package com.podo.climb.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public abstract class ApiResult<T> {

    @JsonProperty
    protected int code;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected T data;

    public ApiResult(Integer code) {
        this.code = code;
    }

    public ApiResult(Integer code, T response) {
        this.code = code;
        this.data = response;
    }
}

