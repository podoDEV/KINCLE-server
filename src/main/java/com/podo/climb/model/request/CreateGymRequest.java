package com.podo.climb.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGymRequest {
    String name;
    String description;
    String address;
    String imageUrl;
    String openingHours;
}
