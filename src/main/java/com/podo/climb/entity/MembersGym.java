package com.podo.climb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MemberGyms")
public class MembersGym {
    @Id
    @Column(name = "members_gym_id")
    private Long membersGymId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "gym_id")
    private Long gymId;

}
