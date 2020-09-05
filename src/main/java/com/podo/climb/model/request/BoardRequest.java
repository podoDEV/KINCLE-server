package com.podo.climb.model.request;

import com.podo.climb.model.BoardType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {
    @ApiModelProperty("제목")
    String title;
    @ApiModelProperty("본문")
    String description;
    @ApiModelProperty("업로드한 이미지 주소")
    String imageUrl;
    @ApiModelProperty("problem, community, notice. 추후 정렬용으로 만듬. 없을 경우 problem")
    BoardType boardType;
    @ApiModelProperty("단계")
    Integer level;
    @ApiModelProperty("암장 정보")
    Long gymId;
}
