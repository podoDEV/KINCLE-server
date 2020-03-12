package com.podo.climb.controller;

import com.podo.climb.model.AuthenticationToken;
import com.podo.climb.model.request.SignInRequest;
import com.podo.climb.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/v1/signin")
    public AuthenticationToken signIn(
            @RequestBody SignInRequest signInRequest,
            HttpSession session) {
        return authenticationService.signIn(signInRequest, session);
    }

    @GetMapping(value = "v1/signout")
    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.signOut(request, response);
    }
}