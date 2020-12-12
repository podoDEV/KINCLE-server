package com.podo.climb.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MemberRole;
import com.podo.climb.model.OauthType;
import com.podo.climb.model.request.MemberRequest;
import lombok.Data;

import java.util.List;

@Data
public class MemberResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;

    private String emailAddress;

    private String nickname;

    private String profileImageUrl;

    private Integer level;

    @JsonSerialize(using = ToStringSerializer.class)
    private OauthType oauthType;

    @JsonSerialize(using = ToStringSerializer.class)
    private List<MemberRole> memberRole;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accessToken;

    public MemberResponse(Member member, String accessToken) {
        this.memberId = member.getMemberId();
        this.emailAddress = member.getEmailAddress();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();
        this.level = member.getLevel();
        this.oauthType = member.getOauthType();
        this.memberRole = member.getMemberRole();
        this.accessToken = accessToken;
    }

}
