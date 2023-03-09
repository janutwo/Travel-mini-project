package com.people.travel.core.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.people.travel.core.exception.CommonErrorCode;
import com.people.travel.core.exception.CustomFileUploadException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;

@Log4j2
@Component
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3Client;

    public String fileUpload(String dirName, MultipartFile file) {
        String randomFileName = dirName + '/' + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            // Upload a text string as a new object.
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            amazonS3Client.putObject(bucket, randomFileName, file.getInputStream(), objectMetadata);


        } catch (AmazonServiceException | IOException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            log.error(e.getMessage());
            throw new CustomFileUploadException(CommonErrorCode.FILE_UPLOAD_FAIL);
        }
        return amazonS3Client.getUrl(bucket, randomFileName).toString();
    }

    public List<String> multipleFileUpload(String dirName, List<MultipartFile> files) throws IOException {

        List<File> convertToFiles = new ArrayList<>();
        List<String> s3Urls = new ArrayList<>();
        TransferManager transferManager = TransferManagerBuilder.standard().withExecutorFactory(()-> Executors.newFixedThreadPool(2)).withS3Client(amazonS3Client).build();

        for (MultipartFile multipartFile : files) {
            byte[] data = multipartFile.getBytes();

            File newFile = File.createTempFile(LocalDate.now().toString(), multipartFile.getOriginalFilename());
            try(FileOutputStream fos = new FileOutputStream(newFile)){
                fos.write(data);
                fos.flush();
            }catch (IOException e){
                log.error(e.getMessage());
            }
            convertToFiles.add(newFile);

        }


        try {

            MultipleFileUpload multipleFileUpload = transferManager.uploadFileList(bucket,
                    dirName, new File("."), convertToFiles);

            multipleFileUpload.waitForCompletion();

            for (Upload u : multipleFileUpload.getSubTransfers()) {
                UploadResult uploadResult = u.waitForUploadResult();
                URL fileUrl = amazonS3Client.getUrl(uploadResult.getBucketName(), uploadResult.getKey());
                s3Urls.add(fileUrl.toString());
            }


        } catch (AmazonServiceException e) {

        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } finally {
            for (File convertToFile : convertToFiles) {
                convertToFile.deleteOnExit();
            }
            transferManager.shutdownNow();
        }
        return s3Urls;
    }

    private File convertToFiles(MultipartFile mfile) throws IOException {

        File file = new File(Objects.requireNonNull(mfile.getOriginalFilename()));
        mfile.transferTo(file);
        return file;
    }

}

