package com.zerotoismail.shorturlservice.service;

import com.zerotoismail.shorturlservice.dto.UrlRequest;
import com.zerotoismail.shorturlservice.exception.UrlNotFoundException;

/**
 * Service Contract defining core URL Shortening and Lookup operations.
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
public interface ShortURLService {

    /**
     * Converts a long URL into a unique shortened URL string.
     * <p>
     * Generates a random alphanumeric key, injects security characters if configured,
     * persists the mapping to the database, and prefixes the result with the application base URL.
     * </p>
     *
     * @param longUrl {@link UrlRequest} containing the original destination URL
     * @return Fully qualified short URL (e.g. {@code http://localhost:8080/api/Xy89Za})
     */
    String convertLongUrlToShort(UrlRequest longUrl);

    /**
     * Retrieves the original destination long URL for a given short URL key.
     *
     * @param shortUrl Unique alphanumeric key identifying the shortened URL
     * @return Original destination long URL string
     * @throws UrlNotFoundException if the provided key does not exist in persistence
     */
    String convertShortUrlToLong(String shortUrl);
}
