package com.fuad.shorten.modules.link;

import com.fuad.shorten.modules.link.dto.response.LinkResponse;
import com.fuad.shorten.shared.dto.GenericResponse;
import com.fuad.shorten.shared.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/link", produces = "application/json")
public class LinkController {
    @Autowired
    ResponseUtil responseUtil;

    @PostMapping
    ResponseEntity<GenericResponse<LinkResponse>> createLink() {
        LinkResponse linkResponse = LinkResponse.builder()
                .shortenLink("https://dummy.co/abc")
                .originalLink("https://google.com")
                .build();

        return responseUtil.response(linkResponse, HttpStatus.CREATED);
    }
}
