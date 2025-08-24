package com.fuad.shorten.modules.link;

import com.fuad.shorten.modules.link.dto.request.LinkRequest;
import com.fuad.shorten.modules.link.dto.response.LinkResponse;

public interface LinkService {
    LinkResponse createLink(LinkRequest dto);
    String getLink(String shortCode);
}
