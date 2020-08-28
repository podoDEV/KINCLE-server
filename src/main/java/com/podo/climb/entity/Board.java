package com.podo.climb.entity;

import com.podo.climb.entity.converter.BoardTypeConverter;
import com.podo.climb.entity.converter.MemberRoleTypeConverter;
import com.podo.climb.model.BoardType;
import com.podo.climb.model.MemberRoleType;
import com.podo.climb.model.request.BoardRequest;
import com.podo.climb.model.response.BoardResponse;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Boards")
public class Board {

    @Id
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "creator")
    private String creator;

    @Column(name = "creator_id")
    private Long creatorId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Calendar createdAt;

    @Column(name = "gym_id")
    private Long gymId;

    @Column(name = "level")
    private Integer level;

    @Convert(converter = BoardTypeConverter.class)
    @Column(name = "type")
    private BoardType boardType;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "follow_count")
    private Integer followCount;

    public BoardResponse toBoardResponse() {
        return BoardResponse.builder()
                            .boardId(this.boardId)
                            .title(this.title)
                            .description(this.description)
                            .imageUrl(this.imageUrl)
                            .createAt(this.createdAt)
                            .creator(this.creator)
                            .level(this.level)
                            .type(this.boardType.toString())
                            .likeCount(this.likeCount)
                            .followCount(this.followCount)
                            .build();
    }

    public void updateBoard(BoardRequest boardRequest) {
        if (boardRequest.getDescription() != null) {
            this.description = boardRequest.getDescription();
        }

        if (boardRequest.getTitle() != null) {
            this.title = boardRequest.getTitle();
        }

        if (boardRequest.getGymId() != null) {
            this.gymId = boardRequest.getGymId();
        }

        if (boardRequest.getImageUrl() != null) {
            this.imageUrl = boardRequest.getImageUrl();
        }

    }
}
