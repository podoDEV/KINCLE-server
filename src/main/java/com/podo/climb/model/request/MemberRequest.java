package com.podo.climb.model.request;

import com.podo.climb.model.OauthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    String nickname;
    String emailAddress;
    String password;
    String token;
    String profileImageUrl;
    Integer level;
    OauthType oauthType;
}
