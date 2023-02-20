package com.people.travel.admin.service;

import com.people.travel.admin.repository.TravelRepository;
import com.people.travel.core.entity.Travel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class FindTravelService {

    private final TravelRepository travelRepository;

    public List<Travel>findAllTravel(){


        return null;
    }
}
