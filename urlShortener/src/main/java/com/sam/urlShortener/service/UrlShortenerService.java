package com.sam.urlShortener.service;

import com.sam.urlShortener.entity.UrlShortener;
import com.sam.urlShortener.exception.ResourceAlreadyExistException;
import com.sam.urlShortener.repository.UrlShortenerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sam.urlShortener.utils.UrlShortenerUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class UrlShortenerService {

    private UrlShortenerRepository urlShortenerRepository;

    public String generateShortenUrl(String url) {
        if (urlShortenerRepository.findByOriginalUrl(url).isPresent())
            throw new ResourceAlreadyExistException(String.format("Requested url - %s already exist.", url));

        UrlShortener urlShortener = UrlShortener.builder()
                .originalUrl(url).expiration(EXPIRATION_TIME)
                .createdAt(CURRENT_LOCAL_DATE_TIME).createdBy(APP_USER).updatedAt(CURRENT_LOCAL_DATE_TIME)
                .updatedBy(APP_USER).build();
        UrlShortener saved = urlShortenerRepository.saveAndFlush(urlShortener);
        String shortenUrl = baseEncode62(saved.getId() + VALUE);
        saved.setShortenedUrl(shortenUrl);
        return urlShortenerRepository.saveAndFlush(saved).getShortenedUrl();

    }

    private String baseEncode62(Long id) {
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE64ENCODE.charAt((int) (id % 62)));
            id /= 62;
        }
        log.info("BaseEncoded62 String - {}", sb);
        return sb.toString();
    }

    public String getOriginalUrl(String shortenedUrl) {
        UrlShortener urlShortener = urlShortenerRepository.findByShortenedUrlAndExpirationAfter(shortenedUrl,
                        CURRENT_LOCAL_DATE_TIME)
                .orElseThrow(() ->
                        new EntityNotFoundException("Request shortened url does not exist or is invalid(expire)."));
        log.info("Record Expiration: {}", urlShortener.getExpiration());
        return urlShortener.getOriginalUrl();
    }
}
