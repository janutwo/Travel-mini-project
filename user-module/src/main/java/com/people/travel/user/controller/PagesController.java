package com.people.travel.user.controller;

import com.people.travel.user.dto.TravelRequestDto;
import com.people.travel.user.service.TraverRegisterService;
import com.people.travel.user.service.UserFindTravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Log4j2
@Controller
@RequiredArgsConstructor
public class PagesController {

    private final UserFindTravelService findService;
    private final TraverRegisterService traverRegisterService;
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

    @GetMapping("/travel-register")
    public String travelRegisterPage() {
        return "pages/travel-register";
    }

    @PostMapping("/travel-register")
    public ResponseEntity<?> register(
            @RequestPart TravelRequestDto.TravelInfo travelInfo,
            @RequestPart(required = false) List<TravelRequestDto.AccommodationInfo> accommodationInfos,
            @RequestPart(required = false) List<MultipartFile> files
    ) {
        traverRegisterService.travelRegister(travelInfo, accommodationInfos, files);
        return ResponseEntity.ok().body(true);
    }
}
