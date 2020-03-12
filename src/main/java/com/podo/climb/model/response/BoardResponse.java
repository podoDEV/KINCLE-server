package com.podo.climb.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardResponse {
    Long boardId;
    String subject;
    String comment;
    String image;
    String creator;
}
