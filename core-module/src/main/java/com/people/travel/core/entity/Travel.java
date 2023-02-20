package com.people.travel.core.entity;

import com.people.travel.core.entity.base.TimeStamped;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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

    @BatchSize(size = 5)
    @OneToMany(mappedBy = "travel")
    @Builder.Default
    private List<TravelDocument> docs = new ArrayList<>();

    @OneToMany(mappedBy = "travel")
    @Builder.Default
    private List<Accommodation> accommodations = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    TravelStatus status;

    public String getTravelDuration(){
        String startDate = this.startDate.toString();
        String endDate = this.endDate.toString();
        return startDate + " ~ " + endDate;
    }
    @Override
    public String toString() {
        return "Travel{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", travelName='" + travelName + '\'' +
                ", description='" + description + '\'' +
                ", region='" + region + '\'' +
                ", participantCount=" + participantCount +
                ", maxMemberCount=" + maxMemberCount +
                ", minMemberCount=" + minMemberCount +
                ", status=" + status +
                '}';
    }

}
