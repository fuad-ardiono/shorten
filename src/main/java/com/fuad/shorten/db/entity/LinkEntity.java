package com.fuad.shorten.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity()
@Table(name = "link")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("id")
    private UUID id;

    @Column(name = "short_code", length = 8)
    @JsonProperty("short_code")
    private String shortCode;

    @Column(name = "short_code_first", length = 1)
    @JsonProperty("short_code_first")
    private char shortCodeFirst;

    @Column(name = "original_url", nullable = false, length = 2048)
    @JsonProperty("original_url")
    private String originalUrl;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    @JsonProperty("expires_at")
    private LocalDateTime expiresAt;
}
