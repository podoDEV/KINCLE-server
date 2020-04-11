package com.podo.climb.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class BoardResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    Long boardId;
    String title;
    String description;
    String imageUrl;
    String creator;
}
