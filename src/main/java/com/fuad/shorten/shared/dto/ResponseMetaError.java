package com.fuad.shorten.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseMetaError<T> {
    @JsonProperty("message")
    String message;

    @JsonProperty("errorBag")
    T errorBag;
}
