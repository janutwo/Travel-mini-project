package com.people.travel.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;


//@SpringBootTest
class UserFindTravelServiceTest {

//    @Autowired
//    UserFindTravelService service;
    @Test
    void findCurrentMonthTravelSchedule() {
        //service.findMonthTravelSchedule();
        LocalDate today = LocalDate.now();
        Month month = today.getMonth();
        System.out.println(month.name());

    }


}