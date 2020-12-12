package com.podo.climb.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.podo.climb.entity.Gym;
import com.podo.climb.entity.Member;
import com.podo.climb.entity.MembersBoard;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberDetailResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long memberId;
    private String emailAddress;
    private String nickname;
    private String profileImageUrl;
    private Integer level;
    private List<GymResponse> gyms;
    private Integer likeCount;
    private Integer solveCount;
    private Integer followCount;

    public MemberDetailResponse(Member member, List<Gym> gyms, List<MembersBoard> membersBoards) {
        this.memberId = member.getMemberId();
        this.emailAddress = member.getEmailAddress();
        this.nickname = member.getNickname();
        this.profileImageUrl = member.getProfileImageUrl();
        this.level = member.getLevel();
        this.gyms = gyms.stream()
                        .map(gym -> new GymResponse(gym.getGymId(), gym.getName()))
                        .collect(Collectors.toList());
        this.likeCount = 0;
        this.solveCount = 0;
        this.followCount = 0;
        membersBoards.forEach(membersBoard -> {
            if (membersBoard.getLikeFlag()) {
                likeCount++;
            }
            if (membersBoard.getSolveFlag()) {
                solveCount++;
            }
            if (membersBoard.getFollowFlag()) {
                followCount++;
            }
        });
    }

    @Data
    @AllArgsConstructor
    public static class GymResponse {
        @JsonSerialize(using = ToStringSerializer.class)
        Long id;
        String name;
    }

}