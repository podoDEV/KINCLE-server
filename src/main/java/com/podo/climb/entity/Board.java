package com.podo.climb.entity;

import com.podo.climb.model.response.BoardResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "Boards")
public class Board {

    @Id
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "comment")
    private String comment;

    @Column(name = "image")
    private String image;

    @Column(name = "creator")
    private String creator;

    public BoardResponse toBoardResponse() {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setBoardId(boardId);
        boardResponse.setSubject(subject);
        boardResponse.setComment(comment);
        boardResponse.setImage(image);
        boardResponse.setCreator(creator);
        return boardResponse;
    }
}
