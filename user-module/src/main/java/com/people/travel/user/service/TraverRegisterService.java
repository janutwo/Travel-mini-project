package com.people.travel.user.service;

import com.people.travel.core.entity.Accommodation;
import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelDocument;
import com.people.travel.core.repository.AccommodationRepository;
import com.people.travel.core.repository.TravelDocumentRepository;
import com.people.travel.core.repository.TravelRepository;
import com.people.travel.core.service.FileUploadService;
import com.people.travel.user.dto.TravelRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor

public class TraverRegisterService {

    private final TravelRepository travelRepository;
    private final AccommodationRepository accommodationRepository;
    private final TravelDocumentRepository travelDocumentRepository;
    private final FileUploadService uploader;

    @Transactional
    public void travelRegister(TravelRequestDto.TravelInfo travelInfo,
                               List<TravelRequestDto.AccommodationInfo> accommodationInfos,
                               List<MultipartFile> docs) {
        //여행 정보 저장
        Travel travel = travelInfo.dtoToEntity();
        travelRepository.save(travel);

        // 여행 숙소 정보 저장
        saveAccommodations(travel, accommodationInfos);

        // 여행 문서 저장
        saveDocs(travel, docs);
    }

    private void saveAccommodations(Travel travel, List<TravelRequestDto.AccommodationInfo> accommodationInfos) {

        if (accommodationInfos == null) return;

        List<Accommodation> accommodations = new ArrayList<>();
        for (TravelRequestDto.AccommodationInfo accommodationInfo : accommodationInfos) {
            Accommodation accommodation = accommodationInfo.dtoToEntity();
            accommodation.setTravel(travel);
            accommodations.add(accommodation);
            accommodationRepository.saveAll(accommodations);
        }
    }

    private void saveDocs(Travel travel, List<MultipartFile> docs) {

        if (docs == null) return;

        List<TravelDocument> documents = new ArrayList<>();
        for (MultipartFile doc : docs) {
            String s3Url = uploader.fileUpload(travel.getStartDate().toString(), doc);
            TravelDocument document = TravelDocument.builder()
                    .fileName(doc.getOriginalFilename())
                    .documentUrl(s3Url)
                    .contentType(doc.getContentType())
                    .build();

            document.setTravel(travel);
            documents.add(document);
        }

        travelDocumentRepository.saveAll(documents);
    }

    public void deleteRegister() {

    }
}
