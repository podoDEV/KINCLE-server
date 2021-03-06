package com.podo.climb.model.request;


import com.podo.climb.model.OauthType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @ApiModelProperty(value = "자체 로그인이 아닐 경우 생략")
    String emailAddress;
    @ApiModelProperty(value = "자체 로그인이 아닐 경우 생략")
    String password;
    @ApiModelProperty(value = "자체 로그인일 경우 생략")
    String token;
    @ApiModelProperty(required = true, value = "self, google, apple, kakao")
    OauthType oauthType;
}
