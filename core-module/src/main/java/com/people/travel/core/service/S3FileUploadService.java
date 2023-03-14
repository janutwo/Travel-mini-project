package com.people.travel.core.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3FileUploadService {
    public String singleFileUpload(String dirName, MultipartFile file);

    public List<String> multipleFileUpload(String dirName, List<MultipartFile> files);
}
