package com.people.travel.core.entity;

import com.people.travel.core.entity.base.TimeStamped;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Travel extends TimeStamped {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String travelName;

    private String description;

    private String region;

    @Builder.Default
    private int participantCount=0;

    @Builder.Default
    private int maxMemberCount=0;

    @Builder.Default
    private int minMemberCount=0;


    @OneToMany(mappedBy = "travel")
    @Builder.Default
    private List<TravelDocument> docs = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    @Builder.Default
    private List<Accommodation> accommodations = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    TravelStatus status;
    
}
