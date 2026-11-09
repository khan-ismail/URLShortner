package com.zerotoismail.shorturlservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * Standardized Error Response Data Transfer Object (DTO).
 * <p>
 * Provides a unified structure for error responses returned by the API
 * across all exception scenarios.
 * </p>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "ErrorDto",
        description = "Unified error response structure returned upon client or server errors"
)
public class ErrorDto {

    /**
     * HTTP status code (e.g. 400, 404, 500).
     */
    @Schema(
            description = "HTTP status code representing the outcome of the failed operation",
            example = "404"
    )
    private int statusCode;

    /**
     * Human-readable error message.
     */
    @Schema(
            description = "Human-readable descriptive message clarifying why the error occurred",
            example = "Not able to find URL for this: Xy89Za"
    )
    private String message;

    /**
     * Timestamp in epoch milliseconds when the error was registered.
     */
    @Schema(
            description = "Timestamp when the failure occurred (epoch milliseconds)",
            example = "1740000000000"
    )
    private long timestamp;
}
