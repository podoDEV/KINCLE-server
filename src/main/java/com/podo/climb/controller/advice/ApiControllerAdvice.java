package com.podo.climb.controller.advice;

import com.podo.climb.exception.ApiFailedException;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.FailedResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiFailedException.class)
    public ApiResult handleException(ApiFailedException afe) {
        return new FailedResult(afe.getCode(), afe.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception ex) {
        log.error("", ex);
        return new FailedResult();
    }
}
