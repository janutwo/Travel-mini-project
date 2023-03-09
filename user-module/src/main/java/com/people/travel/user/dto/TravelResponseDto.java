package com.people.travel.user.dto;

import com.people.travel.core.entity.Accommodation;
import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelDocument;
import com.people.travel.core.entity.base.Coordinate;
import com.people.travel.user.util.Util;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



public class TravelResponseDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class TravelSchedule {
        private Long id;
        private String travelName;
        private String desc;
        private String dateDuration;
        private String startDate;
        private String endDate;

        public static TravelSchedule entityToDto(Travel travel) {
            return TravelSchedule.builder()
                    .id(travel.getId())
                    .travelName(travel.getTravelName())
                    .desc(travel.getDescription())
                    .dateDuration(Util.evoCalendarBadgeDurationFormat(travel.getStartDate(),travel.getEndDate()))
                    .startDate(Util.evoCalendarEventFormat(travel.getStartDate()))
                    .endDate(Util.evoCalendarEventFormat(travel.getEndDate()))
                    .build();
        }
    }

    @Getter
    @ToString
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class TravelDetail{
        Long id;
        String region;
        String travelName;

        @Builder.Default
        String desc = "내용 없음";

        @Builder.Default
        int minCnt=0;

        @Builder.Default
        int maxCnt=0;

        @Builder.Default
        List<TravelDoc> docs = new ArrayList<>();

        @Builder.Default
        List<AccommodationDetail> accommodationDetails = new ArrayList<>();

        public static TravelDetail entityToDto(Travel travel){
            List<AccommodationDetail> details = new ArrayList<>();
            List<TravelDoc> docs = new ArrayList<>();

            for (Accommodation accommodation : travel.getAccommodations()) {
                details.add(AccommodationDetail.entityToDto(accommodation));
            }
            for (TravelDocument doc : travel.getDocs()) {
                docs.add(TravelDoc.entityToDto(doc));
            }

            return TravelDetail.builder()
                    .id(travel.getId())
                    .region(travel.getRegion())
                    .travelName(travel.getTravelName())
                    .desc(travel.getDescription())
                    .minCnt(travel.getMinMemberCount())
                    .maxCnt(travel.getMaxMemberCount())
                    .docs(docs)
                    .accommodationDetails(details)
                    .build();

        }
    }

    @Getter
    @ToString
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(access = AccessLevel.PRIVATE)
    public static class AccommodationDetail{
        Long id;
        LocalDate accDate;
        String fullAddress;
        LocalTime checkIn;
        LocalTime checkOut;
        int price;

        @Builder.Default
        String remark= "내용 없음";

        Coordinate coordinate;

        public static AccommodationDetail entityToDto(Accommodation accommodation){
            return AccommodationDetail.builder()
                    .id(accommodation.getId())
                    .accDate(accommodation.getAccDate())
                    .checkIn(accommodation.getCheckIn())
                    .checkOut(accommodation.getCheckOut())
                    .fullAddress(accommodation.getAddress().fullAddress())
                    .price(accommodation.getPrice())
                    .remark(accommodation.getRemark())
                    .coordinate(accommodation.getCoordinate())
                    .build();
        }
    }

    @Getter
    @ToString
    public static class TravelDoc{

        String id;
        String fileName;
        String filePath;


        private TravelDoc(String fileName, String filePath) {
            this.fileName = fileName;
            this.filePath = filePath;
        }

        public static TravelDoc entityToDto(TravelDocument doc) {
            return new TravelDoc(doc.getFileName(), doc.getDocumentUrl());
        }
    }

}
