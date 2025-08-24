package com.fuad.shorten.modules.link;

import com.fuad.shorten.modules.link.dto.request.LinkRequest;
import com.fuad.shorten.modules.link.dto.response.LinkResponse;
import com.fuad.shorten.shared.BaseController;
import com.fuad.shorten.shared.dto.GenericResponse;
import com.fuad.shorten.shared.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/link", produces = "application/json")
public class LinkController extends BaseController {
    @Autowired
    ResponseUtil responseUtil;

    @Autowired
    LinkService linkService;

    @PostMapping
    ResponseEntity<GenericResponse<LinkResponse>> createLink(
            @Valid @RequestBody LinkRequest dto, BindingResult bindingResult
    ) {
        populateError(bindingResult);

        LinkResponse response = linkService.createLink(dto);

        return responseUtil.response(response, HttpStatus.CREATED);
    }
}
