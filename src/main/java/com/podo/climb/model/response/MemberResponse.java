package com.podo.climb.model.response;

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

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer level;

    @JsonSerialize(using = ToStringSerializer.class)
    private OauthType oauthType;

    @JsonSerialize(using = ToStringSerializer.class)
    private List<MemberRole> memberRole;

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
