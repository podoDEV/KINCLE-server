package com.podo.climb.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FailedResult<T> extends ApiResult<T> {

    public FailedResult() {
        super(500);
        this.data = (T) new ErrorMessage("서버 오류가 발생했습니다.");
    }

    public FailedResult(Integer code, String message) {
        super(code);
        this.data = (T) new ErrorMessage(message);
    }

    class ErrorMessage {
        @JsonProperty
        String message;

        ErrorMessage(String message) {
            this.message = message;
        }
    }
}
