package com.people.travel.core.entity;

import com.people.travel.core.entity.base.Address;
import com.people.travel.core.entity.base.Coordinate;
import com.people.travel.core.entity.base.TimeStamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation extends TimeStamped {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate accDate;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private int price;

    private String remark;

    @Embedded
    Address address;

    @Embedded
    Coordinate coordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAVEL_ID" ,nullable = false)
    private Travel travel;

    @Builder
    private Accommodation(LocalDate accDate, LocalTime checkIn, LocalTime checkOut, int price, String remark, Address address, Coordinate coordinate) {
        this.accDate = accDate;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
        this.remark = remark;
        this.address = address;
        this.coordinate = coordinate;
    }

    //코드 중복 방지 및 한쪽만 연관 관계가 설정 되는 것을 방지,
    // 트랜잭션 커밋 시점에 DB에 반영(영속성 컨텍스트와 DB의 불일치)되기 때문에 객체에서도 연관관계를 위해 양쪽에 데이터를 넣는다.
    public void setTravel(Travel travel){

        if(this.travel!=null){
            this.travel.getAccommodations().remove(this);
        }
        this.travel = travel;
        travel.getAccommodations().add(this);
    }

}
