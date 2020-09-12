package com.podo.climb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.podo.climb.Utils.IdGenerator;
import com.podo.climb.encoder.Sha256PasswordEncoder;
import com.podo.climb.entity.converter.OauthTypeConverter;
import com.podo.climb.exception.ApiFailedException;
import com.podo.climb.model.MemberRoleType;
import com.podo.climb.model.OauthType;
import com.podo.climb.model.request.MemberRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Members")
public class Member {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "level")
    private Integer level;

    @Column(name = "token")
    private String token;

    @Convert(converter = OauthTypeConverter.class)
    @Column(name = "oauth_type")
    private OauthType oauthType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private List<MemberRole> memberRole;

    public Member(MemberRequest memberRequest) {
        Long memberId = IdGenerator.generate();
        Sha256PasswordEncoder sha256PasswordEncoder = new Sha256PasswordEncoder();
        Member member = Member.builder()
                              .memberId(memberId)
                              .nickname(memberRequest.getNickname())
                              .password(sha256PasswordEncoder.encode(memberRequest.getPassword()))
                              .emailAddress(memberRequest.getEmailAddress())
                              .profileImageUrl(memberRequest.getProfileImageUrl())
                              .oauthType(memberRequest.getOauthType())
                              .build();
        MemberRole memberRole = MemberRole.builder()
                                          .memberId(memberId)
                                          .role(MemberRoleType.MEMBER)
                                          .build();

        member.setMemberRole(Collections.singletonList(memberRole));
        if (OauthType.SELF.equals(oauthType)) {
            member.setPassword(memberRequest.getPassword());
        } else if (OauthType.GOOGLE.equals(oauthType) || OauthType.APPLE.equals(oauthType)) {
            member.setPassword(sha256PasswordEncoder.encode(memberRequest.getToken()));
        } else {
            throw new ApiFailedException(400, "Login Type not supported");
        }
    }


    public void updateMember(MemberRequest memberRequest) {
        if (memberRequest.getNickname() != null) {
            this.nickname = memberRequest.getNickname();
        }

        if (memberRequest.getEmailAddress() != null) {
            this.emailAddress = memberRequest.getEmailAddress();
        }

        if (memberRequest.getProfileImageUrl() != null) {
            this.emailAddress = memberRequest.getProfileImageUrl();
        }

        if (memberRequest.getLevel() != null) {
            this.level = memberRequest.getLevel();
        }
    }
}
