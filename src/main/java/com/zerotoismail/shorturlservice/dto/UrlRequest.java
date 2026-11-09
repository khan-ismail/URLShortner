package com.zerotoismail.shorturlservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Request Data Transfer Object (DTO) containing the destination URL to shorten.
 * <p>
 * This object is submitted in the request body of the POST {@code /api/shortUrl} endpoint.
 * </p>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "UrlRequest",
        description = "Request payload model containing the original destination URL to be shortened"
)
public class UrlRequest {

    /**
     * Target long destination URL.
     */
    @Schema(
            description = "The target full long URL to be shortened (e.g. starting with http:// or https://)",
            example = "https://www.zerotoismail.com/learning/java/microservices-architecture-best-practices",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Long URL cannot be empty or blank")
    private String longUrl;
}