package com.people.travel.admin.controller;

import com.people.travel.admin.dto.TravelRequestDto;
import com.people.travel.admin.dto.TravelResponseDto;
import com.people.travel.admin.service.AdminFindTravelService;
import com.people.travel.admin.service.TraverRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PagesController {
    private final TraverRegisterService traverRegisterService;
    private final AdminFindTravelService findTravelService;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page, Model model) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        TravelResponseDto.ScheduledTravelList allDepartTravel = findTravelService.findAllDepartTravelWithPaging(pageRequest);
        model.addAttribute("totalPage", allDepartTravel.getTotalPage());
        model.addAttribute("travelList", allDepartTravel.getList());
        return "index";
    }

    @GetMapping("/travel-register")
    public String travelRegisterPage() {
        return "/travel-register";
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
