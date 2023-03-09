package com.people.travel.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommonErrorCode {
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "S3 객체 에러"),
    CONVERT_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"MultipartFIle can not convert File");
    private final HttpStatus httpStatus;
    private final String message;
}
