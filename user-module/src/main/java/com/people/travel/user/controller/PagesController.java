package com.people.travel.user.controller;

import com.people.travel.user.service.UserFindTravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;


@Log4j2
@Controller
@RequiredArgsConstructor
public class PagesController {

    private final UserFindTravelService findService;
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("calendarEvents", findService.findMonthTravelSchedule(LocalDateTime.now()));
        model.addAttribute("soonTravel", findService.soonScheduledTravel());
        return "pages/index";
    }

    @GetMapping("/travel-schedule")
    public String travelSchedule(){
        return "pages/travel-schedule";
    }
}
