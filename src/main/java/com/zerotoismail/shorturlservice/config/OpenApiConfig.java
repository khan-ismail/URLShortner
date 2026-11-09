package com.zerotoismail.shorturlservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI / Swagger Configuration for Short URL Service.
 * <p>
 * This configuration class defines the OpenAPI 3.0 specification metadata,
 * servers, contact information, licensing, and API version groupings.
 * </p>
 *
 * <h2>API Versioning:</h2>
 * <ul>
 *   <li><b>v1 Group:</b> Covers all core version 1 endpoints (/api/**).</li>
 * </ul>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfig {

    @Value("${app.base_url:http://localhost:8080}")
    private String configuredBaseUrl;

    /**
     * Primary OpenAPI Bean defining global metadata, contact details, servers, and documentation.
     *
     * @return Fully configured {@link OpenAPI} instance
     */
    @Bean
    public OpenAPI shortUrlOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Short URL Service REST API")
                        .version("1.0.0")
                        .description("""
                                # Short URL Service REST API Documentation
                                
                                Welcome to the **Short URL Service** API documentation. This microservice provides high-throughput, 
                                low-latency URL shortening, customized random key encoding, and fast HTTP 302 redirection.
                                
                                ## Key Capabilities:
                                * **URL Shortening:** Converts long URLs into compact alphanumeric keys.
                                * **Configurable Generation Strategy:** Supports custom character pools (numbers, uppercase, lowercase, special symbols).
                                * **Instant Redirection:** Fast retrieval and HTTP 302 redirect to original destination URLs.
                                * **Resilient Architecture:** Centralized exception handling with standardized JSON error responses.
                                
                                ## Versioning Strategy:
                                This API follows **Semantic Versioning (SemVer)**:
                                * Current Active Version: `v1.0.0`
                                * Base path: `/api` or `/api/v1`
                                """)
                        .contact(new Contact()
                                .name("Ismail Khan - ZeroToIsmail")
                                .email("contact@zerotoismail.com")
                                .url("https://github.com/khan-ismail"))
                        .license(new License()
                                .name("Apache License Version 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Source & Documentation Repository")
                        .url("https://github.com/khan-ismail/URLShortner"))
                .servers(List.of(
                        new Server()
                                .url(configuredBaseUrl)
                                .description("Configured Application Server (Base URL)"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server (Default)"),
                        new Server()
                                .url("https://api.zerotoismail.com")
                                .description("Production Server (Live)")
                ))
                .tags(List.of(
                        new Tag()
                                .name("Short URL Management")
                                .description("Endpoints for creating short URLs and handling HTTP 302 redirects")
                ));
    }

    /**
     * Swagger Group for API Version 1 (v1).
     * Filters endpoints mapped under {@code /api/**}.
     *
     * @return {@link GroupedOpenApi} for v1 APIs
     */
    @Bean
    public GroupedOpenApi v1ApiGroup() {
        return GroupedOpenApi.builder()
                .group("v1")
                .displayName("Version 1.0 (Current)")
                .pathsToMatch("/api/**")
                .build();
    }
}
