package com.fuad.shorten.modules.link.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequest {
    @JsonProperty("url")
    public String url;

    @JsonProperty("customShortenLink")
    public String customShortenLink;
}
