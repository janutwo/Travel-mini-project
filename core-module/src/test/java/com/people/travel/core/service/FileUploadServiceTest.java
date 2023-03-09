package com.people.travel.core.service;

import com.people.travel.core.config.S3Config;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = {FileUploadService.class, S3Config.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileUploadServiceTest {
    @Autowired
    private FileUploadService fileUploadService;

    MultipartFile multipartFile;
    List<MultipartFile> multipartFileList;

    @BeforeEach
    void init() throws IOException {
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

        multipartFile = new CommonsMultipartFile(fileItem);

        multipartFileList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            multipartFileList.add(multipartFile);
        }
    }

    @Test
    @DisplayName("s3Client.putObject")
    @Order(1)
    void fileUpload() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (MultipartFile file : multipartFileList) {
            //when
            fileUploadService.fileUpload("test2", file);
        }
        stopWatch.stop();
        System.out.println(String.format("코드 실행 시간: %20dms", stopWatch.getTotalTimeMillis()));
    }

    @Test
    @DisplayName("멀티 업로드")
    @Order(2)
    void multipleUpload() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<String> multi_test2 = fileUploadService.multipleFileUpload("multi_test3", multipartFileList);
        Assertions.assertThat(multi_test2.size()).isEqualTo(4);
        stopWatch.stop();
        System.out.println(String.format("코드 실행 시간: %20dms", stopWatch.getTotalTimeMillis()));

    }
}