package com.sam.urlShortener.repository;

import com.sam.urlShortener.entity.UrlShortener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UrlShortenerRepository extends JpaRepository<UrlShortener, Long> {

    Optional<UrlShortener> findByShortenedUrlAndExpirationAfter(String shortenedUrl, LocalDateTime localDateTime);

    Optional<UrlShortener> findByOriginalUrl(String originalUrl);
}
