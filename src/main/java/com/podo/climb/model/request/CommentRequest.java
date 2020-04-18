package com.podo.climb.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    Long boardId;
    String description;
}
