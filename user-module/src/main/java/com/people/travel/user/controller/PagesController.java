package com.people.travel.user.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Log4j2
@Controller
public class PagesController {

    @GetMapping("/")
    public String home(){
        return "pages/index";
    }

    @GetMapping("/travel-schedule")
    public String travelSchedule(){
        return "pages/travel-schedule";
    }
}
