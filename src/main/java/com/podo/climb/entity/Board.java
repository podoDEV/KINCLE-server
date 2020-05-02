package com.podo.climb.entity;

import com.podo.climb.model.response.BoardResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Calendar createAt;

    @Column(name = "creator")
    private String creator;

    public BoardResponse toBoardResponse() {
        return BoardResponse.builder()
                            .boardId(this.boardId)
                            .title(this.title)
                            .description(this.description)
                            .imageUrl(this.imageUrl)
                            .createAt(this.createAt)
                            .creator(this.creator)
                            .build();
    }
}
