package com.zerotoismail.shorturlservice.controller;

import com.zerotoismail.shorturlservice.dto.ErrorDto;
import com.zerotoismail.shorturlservice.dto.UrlRequest;
import com.zerotoismail.shorturlservice.service.ShortURLService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * REST Controller exposing URL Shortening and Redirection operations.
 * <p>
 * Supports both versioned ({@code /api/v1}) and default ({@code /api}) routes
 * to guarantee backwards compatibility and clean API versioning.
 * </p>
 *
 * <h2>Endpoints:</h2>
 * <ul>
 *   <li><b>POST /api/shortUrl:</b> Generates and returns a shortened URL for the provided target URL.</li>
 *   <li><b>GET /api/{shortUrlKey}:</b> Resolves the shortened key and redirects to the original URL with HTTP 302.</li>
 * </ul>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(
        name = "Short URL Management",
        description = "Core operations for shortening destination URLs, resolving keys, and performing HTTP 302 redirects"
)
public class ShortURLController {

    private final ShortURLService shortURLService;

    /**
     * Converts a full-length destination URL into a shortened URL.
     *
     * @param longUrl {@link UrlRequest} containing the target destination URL
     * @return Fully qualified short URL string
     */
    @PostMapping(
            value = "/shortUrl",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @Operation(
            summary = "Generate short URL from long URL",
            description = """
                    Accepts a target long URL in the request body, executes random key generation using 
                    the configured alphanumeric and security character pools, stores the URL mapping in MySQL, 
                    and returns the generated short URL string.
                    """,
            operationId = "convertLongUrlToShort"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Short URL successfully created and persisted",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(type = "string", example = "http://localhost:8080/api/aB3x9Z")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload or empty/malformed URL",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "ValidationError",
                                    value = """
                                            {
                                              "statusCode": 400,
                                              "message": "Long URL cannot be empty or blank",
                                              "timestamp": 1740000000000
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected internal server error while generating or saving the short URL",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "ServerError",
                                    value = """
                                            {
                                              "statusCode": 500,
                                              "message": "An internal error occurred while processing the request",
                                              "timestamp": 1740000000000
                                            }
                                            """
                            )
                    )
            )
    })
    public String convertLongUrlToShort(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request payload specifying the destination URL to be shortened",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UrlRequest.class),
                            examples = @ExampleObject(
                                    name = "SampleLongUrlRequest",
                                    summary = "Sample URL shortening request",
                                    value = """
                                            {
                                              "longUrl": "https://www.zerotoismail.com/learning/java/microservices-architecture-best-practices"
                                            }
                                            """
                            )
                    )
            )
            @Valid @RequestBody UrlRequest longUrl
    ) {
        return shortURLService.convertLongUrlToShort(longUrl);
    }

    /**
     * Resolves the short URL key and issues an HTTP 302 Found redirect to the destination long URL.
     *
     * @param shortUrlKey Alphanumeric key corresponding to the shortened URL
     * @return {@link ResponseEntity} with HTTP 302 status and Location header set to the destination URL
     */
    @GetMapping("/{shortUrlKey}")
    @Operation(
            summary = "Redirect to original destination URL",
            description = """
                    Looks up the destination long URL associated with the provided short URL key. 
                    If found, returns an HTTP 302 Found status code with the Location header pointing to the target URL.
                    """,
            operationId = "redirectToLongUrl"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "302",
                    description = "Redirection successful. The Location header contains the destination long URL.",
                    headers = @Header(
                            name = "Location",
                            description = "Destination long URL to which the client should redirect",
                            schema = @Schema(type = "string", example = "https://www.zerotoismail.com/learning/java/microservices-architecture-best-practices")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Short URL key not found in the database",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class),
                            examples = @ExampleObject(
                                    name = "NotFoundExample",
                                    summary = "Key not found error example",
                                    value = """
                                            {
                                              "statusCode": 404,
                                              "message": "Not able to find URL for this: invalidKey",
                                              "timestamp": 1740000000000
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error occurred while retrieving URL",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorDto.class)
                    )
            )
    })
    public ResponseEntity<Void> redirectToLongUrl(
            @Parameter(
                    name = "shortUrlKey",
                    description = "The unique short alphanumeric key assigned to the URL",
                    required = true,
                    example = "aB3x9Z"
            )
            @PathVariable String shortUrlKey
    ) {
        String longUrl = shortURLService.convertShortUrlToLong(shortUrlKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();
    }
}
