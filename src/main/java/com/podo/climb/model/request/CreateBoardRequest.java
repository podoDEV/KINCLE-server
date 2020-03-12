package com.podo.climb.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardRequest {
    String subject;
    String comment;
    String image;
}
