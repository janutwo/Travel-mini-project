package com.people.travel.user.service;



import com.people.travel.core.entity.Travel;
import com.people.travel.core.repository.TravelRepository;
import com.people.travel.user.dto.ResponseDto;
import com.people.travel.user.repository.UserTravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserFindTravelService  {

    private final UserTravelRepository travelRepository;

    public List<ResponseDto.TravelSchedule> findMonthTravelSchedule(LocalDateTime month){
        LocalDateTime start = month.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime end = month.with(TemporalAdjusters.lastDayOfMonth());

        List<Travel> allByModifiedDateBetween = travelRepository.findAllByModifiedDateBetween(start, end);

        List<ResponseDto.TravelSchedule> schedules = new ArrayList<>();
        for (Travel travel : allByModifiedDateBetween) {
            schedules.add(ResponseDto.TravelSchedule.entityToDto(travel));
        }
        return schedules;
    }

    public LocalDate soonScheduledTravel(){
        return travelRepository.findAllByCurrentDateAfter(PageRequest.of(0, 1)).get(0);
    }}
