package com.fuad.shorten.modules.link.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkRequest {
    @NotBlank
    @URL
    @JsonProperty("url")
    public String url;
}
