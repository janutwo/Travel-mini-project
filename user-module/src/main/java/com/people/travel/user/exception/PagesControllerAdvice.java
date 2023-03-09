package com.people.travel.user.exception;

import com.people.travel.core.exception.CustomFileUploadException;
import com.people.travel.core.exception.ErrorResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PagesControllerAdvice {

    @ExceptionHandler({RegisterException.class})
    protected ResponseEntity<ErrorResponseEntity>handleRegisterException(){


    }
}
