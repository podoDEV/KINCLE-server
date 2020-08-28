package com.podo.climb.model.request;

import com.podo.climb.model.BoardType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {
    String title;
    String description;
    String imageUrl;
    @ApiModelProperty("problem, community, notice")
    BoardType boardType;
    Integer level;
    Long gymId;
}
