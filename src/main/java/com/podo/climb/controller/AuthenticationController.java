package com.podo.climb.controller;

import com.podo.climb.model.AuthenticationToken;
import com.podo.climb.model.request.ChangePasswordRequest;
import com.podo.climb.model.request.SignInRequest;
import com.podo.climb.model.response.ApiResult;
import com.podo.climb.model.response.SuccessfulResult;
import com.podo.climb.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Api(tags = {"로그인, 로그아웃 API"})
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ApiOperation(value = "로그인", notes = "성공 시 토큰을 반환. `X-Auth-Token` Header 에 추가 시 사용자 권한 획득")
    @PostMapping(value = "/v1/signin")
    public ApiResult<AuthenticationToken> signIn(
            @RequestBody SignInRequest signInRequest,
            @ApiIgnore HttpSession session) {
        return new SuccessfulResult<>(authenticationService.signIn(signInRequest, session));
    }

    @ApiOperation(value = "로그아웃")
    @GetMapping(value = "/v1/signout")
    public ApiResult<?> signOut(HttpServletRequest request,
                                HttpServletResponse response) {
        authenticationService.signOut(request, response);
        return new SuccessfulResult<>();
    }

    @PutMapping(value = "/v1/password")
    public ApiResult<?> changePassword(ChangePasswordRequest changePasswordRequest) {
        authenticationService.changePassword(changePasswordRequest.getPassword());
        return new SuccessfulResult<>();
    }
}