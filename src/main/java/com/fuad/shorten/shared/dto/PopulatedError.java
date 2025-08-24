package com.fuad.shorten.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopulatedError {
    @JsonProperty("field")
    String field;

    @JsonProperty("message")
    String message;

    @JsonProperty("code")
    String code;
}
