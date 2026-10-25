package com.zerotoismail.shorturlservice.service.impl;

import com.zerotoismail.shorturlservice.config.AppConfig;
import com.zerotoismail.shorturlservice.dto.UrlRequest;
import com.zerotoismail.shorturlservice.entity.UrlEntity;
import com.zerotoismail.shorturlservice.exception.UrlNotFoundException;
import com.zerotoismail.shorturlservice.repo.ShortUrlRepository;
import com.zerotoismail.shorturlservice.service.ShortURLService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ShortURLServiceImpl implements ShortURLService {

    private final AppConfig appConfig;
    private final Random random = new SecureRandom();
    private final ShortUrlRepository repository;

    private String characterPool;

    // Initializes the pool safely after Spring fully injects appConfig
    @PostConstruct
    public void init() {
        this.characterPool = appConfig.getShortUrlKeyNum() +
                appConfig.getShortUrlKeyUpper() +
                appConfig.getShortUrlKeyLower();
    }

    @Override
    public String convertLongUrlToShort(UrlRequest longUrl) {
        int targetLength = appConfig.getShortUrlLength();
        StringBuilder sb = new StringBuilder(targetLength);

        // 1. Generate base random string
        for (int i = 0; i < targetLength; i++) {
            int randomIndex = random.nextInt(characterPool.length());
            sb.append(characterPool.charAt(randomIndex)); // FIXED: Using randomIndex instead of 'i'
        }

        // 2. Inject special characters if a secure key pool exists
        String specialKeys = appConfig.getShortUrlKeySec();
        if (specialKeys != null && !specialKeys.isEmpty()) {
            int specialPaddingIndex1 = random.nextInt(specialKeys.length());
            int specialPaddingIndex2 = random.nextInt(specialKeys.length());

            char specialChar1 = specialKeys.charAt(specialPaddingIndex1);
            char specialChar2 = specialKeys.charAt(specialPaddingIndex2);

            // Bounds must be targetLength to ensure safe indices
            int replaceIndex1 = random.nextInt(targetLength);
            int replaceIndex2 = random.nextInt(targetLength);

            sb.setCharAt(replaceIndex1, specialChar1);
            sb.setCharAt(replaceIndex2, specialChar2);
        }
        repository.save(UrlEntity.builder().longURL(longUrl.getLongUrl()).shortURL(sb.toString()).build());
        return appConfig.getBaseUrl() + "/" + sb.toString();
    }

    @Override
    public String convertShortUrlToLong(String shortUrl) {
        UrlEntity urlEntity =  repository.findByShortURL(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Not able to find URL for this: " + shortUrl));
        return urlEntity.getLongURL();
    }
}
