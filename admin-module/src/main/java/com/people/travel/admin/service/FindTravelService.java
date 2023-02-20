package com.people.travel.admin.service;

import com.people.travel.admin.dto.TravelResponseDto;
import com.people.travel.admin.repository.AccommodationRepository;
import com.people.travel.admin.repository.TravelDocumentRepository;
import com.people.travel.admin.repository.TravelRepository;
import com.people.travel.core.entity.Accommodation;
import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelDocument;
import com.people.travel.core.entity.TravelStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class FindTravelService {

    private final TravelRepository travelRepository;


    public TravelResponseDto.ScheduledTravelList findAllDepartTravel(PageRequest pageRequest) {
        Page<Travel> allTravel = travelRepository.findAllTravel(TravelStatus.DEPART, pageRequest);
        List<TravelResponseDto.ScheduledTravel> list = new ArrayList<>();

        for (Travel travel : allTravel.getContent()) {
            TravelResponseDto.ScheduledTravel scheduledTravel = new TravelResponseDto.ScheduledTravel(
                    travel.getTravelDuration(),
                    travel.getTravelName(),
                    travel.getParticipantCount());
            list.add(scheduledTravel);
        }

        return new TravelResponseDto.ScheduledTravelList(allTravel.getTotalPages(), list);
    }

    //
    public TravelResponseDto.TravelDetail findTravelDetail(Long id) {
        Optional<Travel> byId = travelRepository.findTravelByIdWithAccommodations(id);
        Travel travel = byId.orElseThrow(()-> new RuntimeException("해당 데이터가 없습니다."));
        return TravelResponseDto.TravelDetail.entityToDto(travel);
    }
}
