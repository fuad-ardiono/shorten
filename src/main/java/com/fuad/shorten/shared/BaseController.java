package com.fuad.shorten.shared;

import com.fuad.shorten.shared.exception.http.UnprocessableContentException;
import org.springframework.validation.BindingResult;

public class BaseController {
    public void populateError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new UnprocessableContentException(bindingResult.getAllErrors());
        }
    }
}
