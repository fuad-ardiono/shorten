package com.fuad.shorten.shared.exception.http;

import org.springframework.validation.ObjectError;

import java.util.List;

public class UnprocessableContentException extends RuntimeException {
    private final List<ObjectError> data;

    public UnprocessableContentException(List<ObjectError> data) {
        super("Validation error");
        this.data = data;
    }

    public List<ObjectError> getData() {
        return data;
    }
}