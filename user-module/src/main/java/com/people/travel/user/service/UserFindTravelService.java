package com.people.travel.user.service;



import com.people.travel.core.entity.Travel;
import com.people.travel.user.dto.TravelResponseDto;
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

    public List<TravelResponseDto.TravelSchedule> findMonthTravelSchedule(LocalDateTime month){

        //1일
        LocalDateTime start = month.with(TemporalAdjusters.firstDayOfMonth());

        //달의 마지막 일 30, 31
        LocalDateTime end = month.with(TemporalAdjusters.lastDayOfMonth());

        List<Travel> allByModifiedDateBetween = travelRepository.findAllByModifiedDateBetween(start, end);

        List<TravelResponseDto.TravelSchedule> schedules = new ArrayList<>();
        for (Travel travel : allByModifiedDateBetween) {
            schedules.add(TravelResponseDto.TravelSchedule.entityToDto(travel));
            log.info("{}",travel.toString());
        }
        return schedules;
    }

    public LocalDate soonScheduledTravel(){

        LocalDate soonDate;
        PageRequest limit = PageRequest.of(0, 1);

        List<LocalDate> allByCurrentDateAfter = travelRepository.findAllByCurrentDateAfter(limit);

        if(allByCurrentDateAfter.size() == 0){
           soonDate = travelRepository.findRecentlyTravel(limit).get(0);
       }else{
           soonDate = allByCurrentDateAfter.get(0);
       }
        return soonDate;
    }}
