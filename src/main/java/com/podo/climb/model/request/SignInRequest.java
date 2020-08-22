package com.podo.climb.model.request;


import com.podo.climb.model.OauthType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @ApiModelProperty(value = "자체 로그인이 아닐 경우 생략")
    String emailAddress;
    @ApiModelProperty(value = "자체 로그인이 아닐 경우 생략")
    String password;
    @ApiModelProperty(value = "자체 로그인일 경우 생략. self, google, apple, kakao")
    String token;
    @ApiModelProperty(required = true)
    OauthType oauthType;
}
