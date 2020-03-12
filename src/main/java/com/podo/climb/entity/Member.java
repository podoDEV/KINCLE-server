package com.podo.climb.entity;

import com.podo.climb.entity.converter.OauthTypeConverter;
import com.podo.climb.model.OauthType;
import lombok.Getter;
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
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "Members")
public class Member {

    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "password")
    private String password;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

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

}
