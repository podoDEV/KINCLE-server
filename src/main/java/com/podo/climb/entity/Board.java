package com.podo.climb.entity;

import com.podo.climb.model.response.BoardResponse;
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

    public BoardResponse toBoardResponse() {
        return BoardResponse.builder()
                            .boardId(this.boardId)
                            .title(this.title)
                            .description(this.description)
                            .imageUrl(this.imageUrl)
                            .creator(this.creator)
                            .build();
    }
}
