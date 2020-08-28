package com.podo.climb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MembersBoards")
public class MembersBoard {
    @Id
    @Column(name = "members_board_id")
    private Long memberBoardId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "like_flag")
    private Boolean likeFlag;

    @Column(name = "solve_flag")
    private Boolean solveFlag;

    @Column(name = "follow_flag")
    private Boolean followFlag;

    @Column(name = "score")
    private Float score;

}
