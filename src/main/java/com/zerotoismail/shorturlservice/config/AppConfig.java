package com.zerotoismail.shorturlservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties class mapped to the {@code app} prefix in application configuration.
 * <p>
 * Controls the short URL base URL, key length, and character pools used by the generation engine.
 * </p>
 *
 * <h2>Properties Mapping:</h2>
 * <ul>
 *   <li>{@code app.base_url} &rarr; {@code baseUrl}</li>
 *   <li>{@code app.short_url_length} &rarr; {@code shortUrlLength}</li>
 *   <li>{@code app.short_url_key_upper} &rarr; {@code shortUrlKeyUpper}</li>
 *   <li>{@code app.short_url_key_lower} &rarr; {@code shortUrlKeyLower}</li>
 *   <li>{@code app.short_url_key_num} &rarr; {@code shortUrlKeyNum}</li>
 *   <li>{@code app.short_url_key_sec} &rarr; {@code shortUrlKeySec}</li>
 * </ul>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    /**
     * Application domain base URL used to construct full shortened URLs (e.g. {@code http://localhost:8080}).
     */
    private String baseUrl;

    /**
     * Uppercase characters pool (A-Z) used for short key generation.
     */
    private String shortUrlKeyUpper;

    /**
     * Lowercase characters pool (a-z) used for short key generation.
     */
    private String shortUrlKeyLower;

    /**
     * Numeric characters pool (0-9) used for short key generation.
     */
    private String shortUrlKeyNum;

    /**
     * Special security characters pool used for additional key entropy.
     */
    private String shortUrlKeySec;

    /**
     * Target character length for generated short URLs.
     */
    private int shortUrlLength;
}