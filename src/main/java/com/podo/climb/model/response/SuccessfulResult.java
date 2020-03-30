package com.podo.climb.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SuccessfulResult<T> extends ApiResult<T> {

    public SuccessfulResult() {
        super(0);
    }

    public SuccessfulResult(T response) {
        super(0, response);
    }

}
