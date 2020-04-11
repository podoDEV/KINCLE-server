package com.podo.climb.controller.advice;

import com.podo.climb.exception.ApiFailedException;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.FailedResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiFailedException.class)
    public ApiResult handleApiFailedException(ApiFailedException ex) {
        return new FailedResult(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new FailedResult(405, "Method Not Allowed");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ApiResult handleNotFoundError(HttpServletResponse response, NoHandlerFoundException ex) {
        return new FailedResult(404, "Not Found");
    }

    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception ex) {
        log.error("", ex);
        return new FailedResult();
    }
}
