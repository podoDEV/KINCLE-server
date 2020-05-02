package com.podo.climb.entity;

import com.podo.climb.model.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
@Table(name = "Comments")
public class Comment {

    @Id
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Calendar createAt;

    @Column(name = "creator")
    private String creator;

    public CommentResponse toCommentResponse() {
        return CommentResponse.builder()
                              .commentId(this.commentId)
                              .boardId(this.boardId)
                              .description(this.description)
                              .createAt(this.createAt)
                              .creator(this.creator)
                              .build();
    }
}
