package com.people.travel.core.entity;

import com.people.travel.core.entity.base.TimeStamped;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelDocument extends TimeStamped {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID", nullable = false)
    private Travel travel;

    private String fileName;

    private String contentType;

    private String documentUrl;

    @Builder
    public TravelDocument(Long id, String fileName, String contentType, String documentUrl) {
        this.id = id;
        this.fileName = fileName;
        this.contentType = contentType;
        this.documentUrl = documentUrl;
    }

    public void setTravel(Travel travel){
            this.travel = travel;
            travel.getDocs().add(this);
    }
}
