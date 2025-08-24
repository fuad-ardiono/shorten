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
public class MetaPagination {
    @JsonProperty("page")
    Long page;

    @JsonProperty("size")
    Long size;

    @JsonProperty("totalData")
    Long totalData;
}
