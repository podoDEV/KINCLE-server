package com.podo.climb.entity;

import com.podo.climb.model.response.GymResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Gyms")
public class Gym {
    @Id
    @Column(name = "gym_id")
    private Long gymId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "creator_id")
    private Long creatorId;

    public GymResponse toGymResponse() {
        return GymResponse.builder()
                          .gymId(this.gymId)
                          .name(this.name)
                          .description(this.description)
                          .address(this.address)
                          .imageUrl(this.imageUrl)
                          .openingHours(this.openingHours)
                          .creatorId(this.creatorId)
                          .build();
    }

}
