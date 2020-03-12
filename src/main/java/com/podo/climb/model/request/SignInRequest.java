package com.podo.climb.model.request;


import com.podo.climb.model.OauthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    String emailAddress;
    String password;
    String token;
    OauthType oauthType;
}
