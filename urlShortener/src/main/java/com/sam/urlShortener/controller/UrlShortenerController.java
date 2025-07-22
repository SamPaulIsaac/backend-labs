package com.sam.urlShortener.controller;

import com.sam.urlShortener.service.UrlShortenerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/urlShortener/")
@AllArgsConstructor
@Slf4j
public class UrlShortenerController {
    private UrlShortenerService urlShortenerService;

    @PostMapping("/generateShortenUrl")
    public String generateShortenUrl(@RequestParam String url) {
        log.info("Request received to generate shortened url from the original url - {}", url);
        return urlShortenerService.generateShortenUrl(url);
    }

    @GetMapping("/{shortenedUrl}")
    public RedirectView redirect(@PathVariable String shortenedUrl) {
        log.info("Request received to direct to original url.");
        String originalUrl = urlShortenerService.getOriginalUrl(shortenedUrl);
        log.info("Fetched original url - {} for the requested short url - {}", originalUrl, shortenedUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        redirectView.setHttp10Compatible(false); // for 303 instead of 302 if needed
        return redirectView;
    }
}
