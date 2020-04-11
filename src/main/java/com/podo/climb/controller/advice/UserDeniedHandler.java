package com.podo.climb.controller.advice;

import com.podo.climb.exception.ApiFailedException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res,
                       AccessDeniedException ade) {
        throw new ApiFailedException(403, "Forbidden");
    }

}
