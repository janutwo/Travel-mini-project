package com.people.travel.core.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3Client;

    public String fileUpload(String dirName, MultipartFile file) {
        String randomFileName = dirName + '/' + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()){
            // Upload a text string as a new object.
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            amazonS3Client.putObject(bucket, randomFileName, file.getInputStream(), objectMetadata);


        } catch (AmazonServiceException | IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            log.error(e.getMessage());
        }
        return amazonS3Client.getUrl(bucket, randomFileName).toString();
    }

}

