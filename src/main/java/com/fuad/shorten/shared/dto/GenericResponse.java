package com.fuad.shorten.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenericResponse<T> {
    @JsonProperty("meta")
    GenericResponseMeta meta;

    @JsonProperty("data")
    T data;
}

