package com.people.travel.admin.service;

import com.people.travel.admin.repository.TravelRepository;
import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FindTravelServiceTest {

    @Autowired
    TravelRepository travelRepository;

    @Autowired
    FindTravelService findTravelService;

    @Test
    @DisplayName("여행 조회")
    void findAllDepartTravel() {
        findTravelService.findTravelDetail(5L);
    }
}