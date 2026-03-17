package com.harsh.urlshortener.service;

import com.harsh.urlshortener.exception.ResourceNotFoundException;
import com.harsh.urlshortener.model.UrlMapping;
import com.harsh.urlshortener.repository.UrlMappingRepository;
import com.harsh.urlshortener.util.Base62Encoder;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private final UrlMappingRepository repository;

    public UrlShortenerService(UrlMappingRepository repository) {
        this.repository = repository;
    }

    // ===============================
    // CREATE SHORT URL
    // ===============================
    public UrlMapping shortenUrl(String originalUrl) {

        // create new entity
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);

        // first save → generates ID
        mapping = repository.save(mapping);

        // encode ID → shortCode
        String shortCode = Base62Encoder.encode(mapping.getId());
        mapping.setShortCode(shortCode);

        // save again with shortCode
        return repository.save(mapping);
    }

    // ===============================
    // FETCH + REDIRECT
    // ===============================
    public String getOriginalUrl(String shortCode) {

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Short URL not found"));

        // increment click count
        mapping.setClickCount(mapping.getClickCount() + 1);
        repository.save(mapping);

        return mapping.getOriginalUrl();
    }

}