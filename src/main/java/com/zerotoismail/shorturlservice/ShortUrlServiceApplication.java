package com.zerotoismail.shorturlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Short URL Service Spring Boot Application.
 * <p>
 * Bootstraps the application context, initializes embedded servlet container,
 * connects to MySQL database, and exposes REST APIs along with Swagger / OpenAPI UI.
 * </p>
 *
 * <h2>Key URLs:</h2>
 * <ul>
 *   <li>Swagger UI: {@code /swagger-ui.html} or {@code /swagger-ui/index.html}</li>
 *   <li>OpenAPI JSON Spec: {@code /v3/api-docs}</li>
 *   <li>API v1 Group Spec: {@code /v3/api-docs/v1}</li>
 * </ul>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@SpringBootApplication
public class ShortUrlServiceApplication {

    /**
     * Main method that launches the Spring Boot application.
     *
     * @param args Command-line arguments passed during startup
     */
    public static void main(String[] args) {
        SpringApplication.run(ShortUrlServiceApplication.class, args);
    }

}
