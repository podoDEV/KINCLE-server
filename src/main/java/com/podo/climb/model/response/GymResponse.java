package com.podo.climb.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GymResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    Long gymId;
    String name;
    String description;
    String address;
    String imageUrl;
    String openingHours;
    @JsonSerialize(using = ToStringSerializer.class)
    Long creatorId;
}
