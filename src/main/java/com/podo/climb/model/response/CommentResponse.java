package com.podo.climb.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Calendar;

@Getter
@Setter
@Builder
@ToString
public class CommentResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    Long commentId;
    @JsonSerialize(using = ToStringSerializer.class)
    Long boardId;
    String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZZ", timezone = "GMT+09:00")
    Calendar createAt;
    String creator;
}
