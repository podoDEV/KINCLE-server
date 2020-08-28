package com.podo.climb.controller.advice;

import com.podo.climb.exception.ApiFailedException;
import com.podo.climb.model.response.FailedResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
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
    public FailedResult<FailedResult.ErrorMessage> handleApiFailedException(ApiFailedException ex) {
        return new FailedResult<>(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public FailedResult<FailedResult.ErrorMessage> handleBadCredentialsException(Exception ex) {
        return new FailedResult<>(400, "Wrong email or password");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class})
    public FailedResult<FailedResult.ErrorMessage> handleHBadRequestException(Exception ex) {
        log.error("", ex);
        return new FailedResult<>(400, "Bad Request");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public FailedResult<FailedResult.ErrorMessage> handleNotFoundError(HttpServletResponse response, Exception ex) {
        return new FailedResult<>(404, "Not Found");
    }

    @ExceptionHandler(Exception.class)
    public FailedResult<FailedResult.ErrorMessage> handleException(Exception ex) {
        log.error("", ex);
        return new FailedResult<>();
    }
}
