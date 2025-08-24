package com.fuad.shorten.modules.link;

import com.fuad.shorten.db.entity.LinkEntity;
import com.fuad.shorten.db.repository.LinkRepository;
import com.fuad.shorten.modules.link.dto.request.LinkRequest;
import com.fuad.shorten.modules.link.dto.response.LinkResponse;
import com.fuad.shorten.shared.exception.http.NotFoundException;
import com.fuad.shorten.shared.utils.ShortCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    ShortCodeUtil shortCodeUtil;

    @Autowired
    LinkRepository linkRepository;

    @Autowired
    Environment environment;

    public LinkResponse createLink(LinkRequest dto) {
        String shortenCode = shortCodeUtil.getShortCode(dto.url);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireAt = now.plusDays(3);

        LinkEntity.LinkEntityBuilder linkBuilder = LinkEntity.builder()
                .shortCode(shortenCode)
                .shortCodeFirst(shortenCode.charAt(0))
                .originalUrl(dto.url)
                .expiresAt(expireAt);

        linkRepository.save(linkBuilder.build());

        String shortenLink = String.format("%s/%s", environment.getProperty("config.base-url"), shortenCode);

        return LinkResponse.builder()
                .originalLink(dto.url)
                .shortenLink(shortenLink)
                .build();
    }

    public String getLink(String shortCode) {
        Boolean isExist = linkRepository.existsByShortCode(shortCode);

        if (isExist) {
            return linkRepository.findFirstByShortCode(shortCode)
                    .map(LinkEntity::getOriginalUrl)
                    .orElseThrow(() -> new NotFoundException("Link not found"));
        }

        throw new NotFoundException("Link not found");
    }
}
