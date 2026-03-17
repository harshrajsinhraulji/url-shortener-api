package com.harsh.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShortenUrlResponse {

    private String shortUrl;      // full URL users will use
    private String shortCode;     // encoded identifier
    private String originalUrl;   // original input
    private LocalDateTime createdAt;
    private Long clickCount;
}