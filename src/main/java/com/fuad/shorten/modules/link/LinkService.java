package com.fuad.shorten.modules.link;

import com.fuad.shorten.modules.link.dto.request.LinkRequest;
import com.fuad.shorten.modules.link.dto.response.LinkResponse;

public interface LinkService {
    public LinkResponse createLink(LinkRequest dto);
    public String getLink(String shortCode);
}
