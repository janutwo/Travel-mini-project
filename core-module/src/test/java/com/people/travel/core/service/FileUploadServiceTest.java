package com.people.travel.core.service;

import com.people.travel.core.config.S3Config;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;


@SpringBootTest(classes = {FileUploadService.class, S3Config.class})
class FileUploadServiceTest {
    @Autowired
    private FileUploadService fileUploadService;

    @Test
    void fileUpload() throws IOException {

        //given
        String filePath = "src/test/resources/test.pdf";
        File file = new File(filePath);
        FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        try (InputStream input = new FileInputStream(file)) {
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        //when
        fileUploadService.fileUpload("test", multipartFile);
    }
}