package com.people.travel.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_DURATION_DATE(HttpStatus.BAD_REQUEST, "종료일이 시작일 보다 빠르거나, 시작일이 종료일 보다 빠릅니다."),
    INVALID_PARTICIPANT_COUNT(HttpStatus.BAD_REQUEST, "최소인원이 최대 인원보다 많거나, 최대 인원이 최소 인원보다 적습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
