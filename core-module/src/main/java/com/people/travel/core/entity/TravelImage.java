package com.people.travel.core.entity;

import com.people.travel.core.entity.base.TimeStamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TravelImage extends TimeStamped {

    @Id
    @GeneratedValue
    private Long id;

    private String travelImgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID", nullable = false)
    private Travel travel;

    private boolean isTitle = false;

}
