package com.people.travel.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.people.travel.user.dto.TravelRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PagesControllerTest {

    @Autowired
    PagesController pagesController;

    @Autowired
    MockMvc mvc;



    @Test
    @DisplayName("여행 등록")
    @Transactional
    void register() throws Exception {

        //given
        TravelRequestDto.TravelInfo travelInfo = new TravelRequestDto.TravelInfo();
        travelInfo.setTravelName("수원 여행");
        travelInfo.setDescription("수원 여행을 즐겨보자~~");
        travelInfo.setStartDate(LocalDate.of(2023, 3, 10));
        travelInfo.setEndDate(LocalDate.of(2023, 3, 10));
        travelInfo.setRegion("공주");
        travelInfo.setMaxMemberCount(12);
        travelInfo.setMinMemberCount(4);

        TravelRequestDto.AccommodationInfo[] accArr = new TravelRequestDto.AccommodationInfo[1];
        TravelRequestDto.AccommodationInfo acc = new TravelRequestDto.AccommodationInfo();
        acc.setAccDate(LocalDate.of(2023, 2, 19));
        acc.setAddress("경기도 수원시 장안구 만석로 29");
        acc.setDetailAddress("711동 502호");
        acc.setExtraAddress("(성우우방아파트)");
        acc.setPrice(120000);
        acc.setPostcode("16612");
        acc.setDetailAddress("711동 502호");
        acc.setCheckIn(LocalTime.of(11, 0));
        acc.setCheckOut(LocalTime.of(11, 0));
        acc.setPosX(37.246823);
        acc.setPosY(46.265778);
        accArr[0] = acc;

        String filePath = "src/test/resources/test.pdf";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MockMultipartFile file = new MockMultipartFile("test.pdf","test.pdf","application/pdf",fileInputStream);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        String travelInfoJson = mapper.writeValueAsString(travelInfo);
        String accJson = mapper.writeValueAsString(accArr);
        MockMultipartFile travelJSON = new MockMultipartFile("travelInfo", "travelInfo", "application/json", travelInfoJson.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile accJSON = new MockMultipartFile("accommodationInfos", "accommodationInfos", "application/json", accJson.getBytes(StandardCharsets.UTF_8));

        //when
        mvc.perform(multipart("/travel-register")
                        .file(file)
                        .file(travelJSON)
                        .file(accJSON)
                )
                .andExpect(status().isOk());

    }
}