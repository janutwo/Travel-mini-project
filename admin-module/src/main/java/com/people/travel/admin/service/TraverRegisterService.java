package com.people.travel.admin.service;

import com.people.travel.admin.dto.TravelRequestDto;
import com.people.travel.admin.repository.AccommodationRepository;
import com.people.travel.admin.repository.TravelDocumentRepository;
import com.people.travel.admin.repository.TravelRepository;
import com.people.travel.core.entity.Accommodation;
import com.people.travel.core.entity.Travel;
import com.people.travel.core.entity.TravelDocument;
import com.people.travel.core.service.FileUploadService;
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
@Transactional
public class TraverRegisterService {

    private final TravelRepository travelRepository;
    private final AccommodationRepository accommodationRepository;
    private final TravelDocumentRepository travelDocumentRepository;
    private final FileUploadService uploader;

    public void travelRegister(TravelRequestDto.TravelInfo travelInfo,
                               List<TravelRequestDto.AccommodationInfo> accommodationInfos,
                               List<MultipartFile> docs) {

        Travel travel = travelInfo.dtoToEntity();
        travelRepository.save(travel);

        if(accommodationInfos==null)return;
        List<Accommodation> accommodations = new ArrayList<>();
        for (TravelRequestDto.AccommodationInfo accommodationInfo : accommodationInfos) {
            Accommodation accommodation = accommodationInfo.dtoToEntity();
            accommodation.setTravel(travel);
            accommodations.add(accommodation);

        }
        accommodationRepository.saveAll(accommodations);

        if(docs==null) return;
        List<TravelDocument> documents = new ArrayList<>();
        for (MultipartFile doc : docs) {
            log.info(doc.getContentType());
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
