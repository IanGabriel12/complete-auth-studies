package com.iangabrieldev.spring_boot_auth.expection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    @Autowired ApiExceptionMapper apiExceptionMapper;
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiExceptionView> handleApiException(ApiException apiException) {
        return new ResponseEntity<>(apiExceptionMapper.toApiExceptionView(apiException), apiException.getHttpStatus());
    }
}
