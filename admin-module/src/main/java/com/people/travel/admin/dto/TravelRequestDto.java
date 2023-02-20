package com.people.travel.admin.dto;

import com.people.travel.core.entity.Accommodation;
import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelStatus;
import com.people.travel.core.entity.base.Address;
import com.people.travel.core.entity.base.Coordinate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;


public class TravelRequestDto {

    @ToString
    @Getter
    @Setter
    public static class TravelInfo {

        private LocalDate startDate;

        private LocalDate endDate;

        private String travelName;

        private String description;

        private String region;

        private int maxMemberCount;

        private int minMemberCount;

        public Travel dtoToEntity() {
            return Travel.builder()
                    .startDate(startDate)
                    .endDate(endDate)
                    .travelName(travelName)
                    .description(description)
                    .region(region)
                    .maxMemberCount(maxMemberCount)
                    .minMemberCount(minMemberCount)
                    .status(TravelStatus.DEPART)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Doc {
        private String fileName;

        private String extension;

        private String documentUrl;

    }


    @ToString
    @Getter
    @Setter
    public static class AccommodationInfo {

        private LocalDate accDate;

        private LocalTime checkIn;

        private LocalTime checkOut;

        private int price;

        private String remark;

        private String address;

        private String detailAddress;

        private String extraAddress;

        private String postcode;

        private String mapLink;

        private double posX;

        private double posY;

        public Accommodation dtoToEntity() {

            Address accAddress = Address.builder()
                    .address(address)
                    .detailAddress(detailAddress)
                    .extraAddress(extraAddress)
                    .postcode(postcode)
                    .mapLink(mapLink)
                    .build();

            Coordinate coordinate = new Coordinate(posX, posY);

            return Accommodation.builder()
                    .accDate(accDate)
                    .checkIn(checkIn)
                    .checkOut(checkOut)
                    .price(price)
                    .remark(remark)
                    .address(accAddress)
                    .coordinate(coordinate)
                    .build();
        }
    }
}
