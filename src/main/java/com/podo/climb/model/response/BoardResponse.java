package com.podo.climb.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.podo.climb.entity.MembersBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Calendar;
import java.util.Optional;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    Long boardId;
    String title;
    String description;
    String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZ", timezone = "GMT+09:00")
    Calendar createAt;
    String creator;
    Integer level;
    String type;
    Integer likeCount;
    Integer followCount;
    Boolean likeFlag;
    Boolean solveFlag;
    Boolean followFlag;
    float avgScore;
    float myScore;

    public void setFlags(MembersBoard membersBoard) {
        if (membersBoard != null) {
            this.likeFlag = Optional.ofNullable(membersBoard.getLikeFlag()).orElse(false);
            this.solveFlag = Optional.ofNullable(membersBoard.getSolveFlag()).orElse(false);
            this.followFlag = Optional.ofNullable(membersBoard.getFollowFlag()).orElse(false);
            this.myScore = Optional.ofNullable(membersBoard.getScore()).orElse(0F);
        } else {
            this.likeFlag = false;
            this.solveFlag = false;
            this.followFlag = false;
            this.myScore = 0;
        }
    }
}
