package com.fuad.shorten.modules.link.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LinkResponse {
    @JsonProperty("shortCode")
    String shortCode;

    @JsonProperty("shortenLink")
    String shortenLink;

    @JsonProperty("originalLink")
    String originalLink;
}
