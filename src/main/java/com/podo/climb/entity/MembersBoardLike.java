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
@Table(name = "MembersBoardLikes")
public class MembersBoardLike {
    @Id
    @Column(name = "members_board_like_id")
    private Long memberFavoriteId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "board_id")
    private Long boardId;

}
