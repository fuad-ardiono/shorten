package com.fuad.shorten.shared.utils;

import com.fuad.shorten.shared.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {
    public <T> ResponseEntity<GenericResponse<T>> response(T data, HttpStatus status) {
        ResponseMetaHttp.ResponseMetaHttpBuilder responseMetaHttp = ResponseMetaHttp.builder()
                .statusCode(status.value())
                .statusText(status.getReasonPhrase());

        GenericResponseMeta.GenericResponseMetaBuilder responseMeta = GenericResponseMeta.builder()
                .success(true)
                .http(responseMetaHttp.build());

        GenericResponse<T> genericResponse = new GenericResponse<>();
        genericResponse.setData(data);
        genericResponse.setMeta(responseMeta.build());

        return new ResponseEntity<>(genericResponse, status);
    }

    public <T> ResponseEntity<GenericResponse<T>> response(
            T data,
            MetaPagination metaPagination,
            HttpStatus status
    ) {
        ResponseMetaHttp.ResponseMetaHttpBuilder responseMetaHttp = ResponseMetaHttp.builder()
                .statusCode(status.value())
                .statusText(status.getReasonPhrase());

        ResponseMetaPagination responseMetaPagination = new ResponseMetaPagination();
        responseMetaPagination.setPage(metaPagination.getPage());
        responseMetaPagination.setSize(metaPagination.getSize());
        responseMetaPagination.setTotalData(metaPagination.getTotalData());

        GenericResponseMeta.GenericResponseMetaBuilder responseMeta = GenericResponseMeta.builder()
                .success(true)
                .http(responseMetaHttp.build())
                .pagination(responseMetaPagination);

        GenericResponse<T> genericResponse = new GenericResponse<>();
        genericResponse.setData(data);
        genericResponse.setMeta(responseMeta.build());

        return new ResponseEntity<>(genericResponse, status);
    }

    public <T> ResponseEntity<GenericResponse<T>> response(
            T data,
            Exception exception,
            HttpStatus status
    ) {
        ResponseMetaHttp.ResponseMetaHttpBuilder responseMetaHttp = ResponseMetaHttp.builder()
                .statusCode(status.value())
                .statusText(status.getReasonPhrase());

        ResponseMetaError.ResponseMetaErrorBuilder responseMetaError = ResponseMetaError.builder()
                .message(exception.getMessage());

        GenericResponseMeta.GenericResponseMetaBuilder responseMeta = GenericResponseMeta.builder()
                .success(false)
                .http(responseMetaHttp.build())
                .error(responseMetaError.build());

        GenericResponse<T> genericResponse = new GenericResponse<>();
        genericResponse.setData(data);
        genericResponse.setMeta(responseMeta.build());

        return new ResponseEntity<>(genericResponse, status);
    }
}
