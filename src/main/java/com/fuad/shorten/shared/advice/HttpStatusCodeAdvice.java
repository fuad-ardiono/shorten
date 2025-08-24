package com.fuad.shorten.shared.advice;

import com.fuad.shorten.shared.dto.GenericResponse;
import com.fuad.shorten.shared.exception.NotFoundException;
import com.fuad.shorten.shared.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpStatusCodeAdvice {
    @Autowired
    ResponseUtil responseUtil;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GenericResponse<String>> handleResourceNotFound(NotFoundException ex) {
        return responseUtil.response(null, ex, HttpStatus.NOT_FOUND);
    }
}
