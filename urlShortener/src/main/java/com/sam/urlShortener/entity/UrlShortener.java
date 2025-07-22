package com.sam.urlShortener.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_shortener")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlShortener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortenedUrl;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime expiration;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
