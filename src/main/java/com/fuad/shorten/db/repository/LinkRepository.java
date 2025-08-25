package com.fuad.shorten.db.repository;

import com.fuad.shorten.db.entity.LinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<LinkEntity, UUID> {
    Boolean existsByShortCodeFirstAndShortCode(char shortCodeFirst, String shortCode);

    Optional<LinkEntity> findFirstByShortCodeFirstAndShortCode(char shortCodeFirst, String shortCode);
}
