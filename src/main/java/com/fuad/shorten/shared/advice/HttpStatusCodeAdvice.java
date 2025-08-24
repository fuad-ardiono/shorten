package com.fuad.shorten.shared.advice;

import com.fuad.shorten.shared.dto.GenericResponse;
import com.fuad.shorten.shared.dto.PopulatedError;
import com.fuad.shorten.shared.exception.http.NotFoundException;
import com.fuad.shorten.shared.exception.http.UnprocessableContentException;
import com.fuad.shorten.shared.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HttpStatusCodeAdvice {
    @Autowired
    ResponseUtil responseUtil;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GenericResponse<String>> handleNotFound(NotFoundException ex) {
        return responseUtil.response(null, ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnprocessableContentException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<GenericResponse<Object>> handleUnprocessableContent(UnprocessableContentException ex) {
        List<PopulatedError> populatedErrorList = ex.getData().stream()
                .map((obj) -> {
                    PopulatedError.PopulatedErrorBuilder populatedError = PopulatedError.builder();
                    if (obj instanceof org.springframework.validation.FieldError fieldError) {
                        String messageFormat = String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());

                        populatedError.code(fieldError.getCode())
                                .message(messageFormat)
                                .field(fieldError.getField());
                    }
                    return populatedError.build();
                })
                .toList();

        return responseUtil.response(populatedErrorList, ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
