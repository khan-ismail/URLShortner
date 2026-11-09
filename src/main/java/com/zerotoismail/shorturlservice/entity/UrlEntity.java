package com.zerotoismail.shorturlservice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * JPA Entity mapping the {@code urlTbl} relational table.
 * <p>
 * Represents the persistent storage model for mapping long destination URLs
 * to uniquely generated short URL keys.
 * </p>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@Entity
@Table(name = "urlTbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(
        name = "UrlEntity",
        description = "Relational database entity storing the mapping between original long URLs and generated short URL keys"
)
public class UrlEntity {

    /**
     * Unique database primary key identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Auto-incremented unique database primary key",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    /**
     * The original, full-length destination URL.
     */
    @Column(columnDefinition = "TEXT", nullable = false)
    @Schema(
            description = "Full original destination web address stored as text",
            example = "https://www.zerotoismail.com/learning/java/microservices-architecture-best-practices",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String longURL;

    /**
     * The generated short URL alphanumeric key (maximum 20 characters, unique).
     */
    @Column(length = 20, nullable = false, unique = true)
    @Schema(
            description = "Unique alphanumeric short URL identifier key",
            example = "Xy89Za",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String shortURL;
}