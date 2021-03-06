package com.podo.climb.entity;

import com.podo.climb.entity.converter.MemberRoleTypeConverter;
import com.podo.climb.entity.converter.OauthTypeConverter;
import com.podo.climb.model.MemberRoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MemberRole")
public class MemberRole {
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Convert(converter = MemberRoleTypeConverter.class)
    @Column(name = "role")
    private MemberRoleType role;

}
