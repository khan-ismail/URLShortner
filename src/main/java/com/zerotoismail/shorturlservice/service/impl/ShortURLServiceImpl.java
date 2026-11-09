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

/**
 * Production implementation of {@link ShortURLService}.
 * <p>
 * Implements random alphanumeric key generation with optional special character injection,
 * persists URL mappings using {@link ShortUrlRepository}, and builds redirect-ready short URLs.
 * </p>
 *
 * <h2>Key Generation Algorithm:</h2>
 * <ol>
 *   <li>Constructs a unified character pool from uppercase, lowercase, and numeric keys.</li>
 *   <li>Iteratively selects random characters up to {@code shortUrlLength} using {@link SecureRandom}.</li>
 *   <li>If special security characters are configured, randomly injects two special characters.</li>
 *   <li>Persists the entity to MySQL database via {@link ShortUrlRepository}.</li>
 *   <li>Returns the concatenated {@code baseUrl + "/" + shortUrlKey}.</li>
 * </ol>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ShortURLServiceImpl implements ShortURLService {

    private final AppConfig appConfig;
    private final Random random = new SecureRandom();
    private final ShortUrlRepository repository;

    private String characterPool;

    /**
     * Initializes the character pool after Spring dependency injection is complete.
     */
    @PostConstruct
    public void init() {
        this.characterPool = appConfig.getShortUrlKeyNum() +
                appConfig.getShortUrlKeyUpper() +
                appConfig.getShortUrlKeyLower();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertLongUrlToShort(UrlRequest longUrl) {
        int targetLength = appConfig.getShortUrlLength();
        StringBuilder sb = new StringBuilder(targetLength);

        // 1. Generate base random string from character pool
        for (int i = 0; i < targetLength; i++) {
            int randomIndex = random.nextInt(characterPool.length());
            sb.append(characterPool.charAt(randomIndex));
        }

        // 2. Inject special characters if a secure key pool exists
        String specialKeys = appConfig.getShortUrlKeySec();
        if (specialKeys != null && !specialKeys.isEmpty()) {
            int specialPaddingIndex1 = random.nextInt(specialKeys.length());
            int specialPaddingIndex2 = random.nextInt(specialKeys.length());

            char specialChar1 = specialKeys.charAt(specialPaddingIndex1);
            char specialChar2 = specialKeys.charAt(specialPaddingIndex2);

            // Safe bound within targetLength
            int replaceIndex1 = random.nextInt(targetLength);
            int replaceIndex2 = random.nextInt(targetLength);

            sb.setCharAt(replaceIndex1, specialChar1);
            sb.setCharAt(replaceIndex2, specialChar2);
        }

        // 3. Save mapping in database
        repository.save(UrlEntity.builder()
                .longURL(longUrl.getLongUrl())
                .shortURL(sb.toString())
                .build());

        // 4. Return complete short URL
        return appConfig.getBaseUrl() + "/" + sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String convertShortUrlToLong(String shortUrl) {
        UrlEntity urlEntity = repository.findByShortURL(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Not able to find URL for this: " + shortUrl));
        return urlEntity.getLongURL();
    }
}
