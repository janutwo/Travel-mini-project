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
import java.util.UUID;
import java.util.concurrent.Executors;

@Log4j2
@Component
@RequiredArgsConstructor
public class S3FileUploadServiceImp implements S3FileUploadService {

    @Value("${s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3Client;

    public String singleFileUpload(String dirName, MultipartFile file) {
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

    public List<String> multipleFileUpload(String dirName, List<MultipartFile> files) {

        List<File> convertToFiles = multipartFileListConvertToFileList(files);
        MultipleFileUpload multipleFileUpload;
        TransferManager transferManager =TransferManagerBuilder
                .standard()
                .withExecutorFactory(
                        ()-> Executors.newFixedThreadPool(2)
                )
                .withS3Client(amazonS3Client).build();
        try {

            multipleFileUpload = transferManager.uploadFileList(bucket,
                    dirName, new File("."), convertToFiles);

            multipleFileUpload.waitForCompletion();

        } catch (AmazonServiceException e) {
            // Amazon Service Error
            throw new CustomFileUploadException(CommonErrorCode.S3_SERVICE_ERROR);

        } catch (InterruptedException e) {
            // uploading error
            throw new CustomFileUploadException(CommonErrorCode.FILE_UPLOAD_FAIL);

        } finally {
            eraseTmpFile(convertToFiles);
            transferManager.shutdownNow();
        }

        return multipleFileUpload.isDone() ? getMultipleUploadFileUrl(multipleFileUpload) : new ArrayList<>();
    }

    private List<File> multipartFileListConvertToFileList(List<MultipartFile> multipartFileList) {

        if (multipartFileList == null || multipartFileList.size() == 0) return new ArrayList<>();

        List<File> convertToFiles = new ArrayList<>();

        try {

            for (MultipartFile multipartFile : multipartFileList) {

                byte[] data = multipartFile.getBytes();
                String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
                File newFile = File.createTempFile(UUID.randomUUID().toString(), ext);
                FileOutputStream fos = new FileOutputStream(newFile);

                fos.write(data);
                fos.flush();
                fos.close();

                convertToFiles.add(newFile);
            }

        } catch (IOException e) {
            throw new CustomFileUploadException(CommonErrorCode.CONVERT_TMP_FILE_ERROR);
        }

        return convertToFiles;
    }

    private List<String> getMultipleUploadFileUrl(MultipleFileUpload multipleFileUpload) {

        List<String> s3Urls = new ArrayList<>();
        for (Upload u : multipleFileUpload.getSubTransfers()) {

            UploadResult uploadResult;
            try {

                uploadResult = u.waitForUploadResult();

                if (uploadResult == null) throw new RuntimeException();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            URL fileUrl = amazonS3Client.getUrl(uploadResult.getBucketName(), uploadResult.getKey());
            s3Urls.add(fileUrl.toString());
        }
        return s3Urls;
    }

    private void eraseTmpFile(List<File> tmpFiles) {

        for (File tmpFile : tmpFiles) {
            tmpFile.deleteOnExit();
        }
    }
}

