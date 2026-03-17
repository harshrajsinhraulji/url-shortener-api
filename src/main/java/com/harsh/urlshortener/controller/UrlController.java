package com.harsh.urlshortener.controller;

import com.harsh.urlshortener.dto.ShortenUrlRequest;
import com.harsh.urlshortener.dto.ShortenUrlResponse;
import com.harsh.urlshortener.model.UrlMapping;
import com.harsh.urlshortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
public class UrlController {

    @Value("${app.base-url}")
    private String baseUrl;

    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }

    // ===============================
    // POST /shorten
    // ===============================
    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@Valid @RequestBody ShortenUrlRequest request) {

        // extract URL from DTO
        String originalUrl = request.getUrl();

        // service returns full entity now
        UrlMapping mapping = service.shortenUrl(originalUrl);

        String shortUrl = baseUrl + "/" + mapping.getShortCode();

        // build response DTO
        ShortenUrlResponse response = new ShortenUrlResponse(
                shortUrl,
                mapping.getShortCode(),
                mapping.getOriginalUrl(),
                mapping.getCreatedAt(),
                mapping.getClickCount()
        );

        return ResponseEntity.ok(response);
    }

    // ===============================
    // GET /{shortCode}
    // ===============================
    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponse> redirect(@PathVariable String shortCode) {

        String originalUrl = service.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(302)
                .location(URI.create(originalUrl))
                .build();
    }
}