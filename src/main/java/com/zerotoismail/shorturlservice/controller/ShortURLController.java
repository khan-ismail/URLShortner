package com.zerotoismail.shorturlservice.controller;

import com.zerotoismail.shorturlservice.dto.UrlRequest;
import com.zerotoismail.shorturlservice.service.ShortURLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShortURLController {

    private final ShortURLService shortURLService;

    @PostMapping("/shortUrl")
    public String convertLongUrlToShort(@RequestBody UrlRequest longUrl) {
        return shortURLService.convertLongUrlToShort(longUrl);
    }

    @GetMapping("/{shortUrlKey}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrlKey) {
        String longUrl = shortURLService.convertShortUrlToLong(shortUrlKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }

}
