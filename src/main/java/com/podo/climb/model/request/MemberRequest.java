package com.podo.climb.model.request;

import com.podo.climb.model.OauthType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberRequest {
    String nickname;
    String emailAddress;
    String password;
    String token;
    String profileImageUrl;
    List<Long> gymIds;
    Integer level;
    OauthType oauthType;
}
