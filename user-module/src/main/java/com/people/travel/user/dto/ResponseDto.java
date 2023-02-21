package com.people.travel.user.dto;

import com.people.travel.core.entity.Travel;
import com.people.travel.user.util.Util;
import lombok.*;

import java.time.LocalDate;

public class ResponseDto {

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
}
