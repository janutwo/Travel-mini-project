package com.people.travel.admin.service;

import com.people.travel.core.repository.TravelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FindTravelServiceTest {

    @Autowired
    TravelRepository travelRepository;

    @Autowired
    AdminFindTravelService findTravelService;

    @Test
    @DisplayName("여행 조회")
    void findAllDepartTravel() {
        findTravelService.findTravelDetail(5L);
    }
}