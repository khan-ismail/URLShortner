package com.zerotoismail.shorturlservice.repo;

import com.zerotoismail.shorturlservice.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for managing {@link UrlEntity} instances.
 * <p>
 * Provides CRUD operations and custom query execution against the {@code urlTbl} database table.
 * </p>
 *
 * @author ZeroToIsmail
 * @version 1.0.0
 */
@Repository
public interface ShortUrlRepository extends JpaRepository<UrlEntity, Integer> {

    /**
     * Looks up an existing URL record by its unique shortened key.
     * <p>
     * Used by the redirection workflow to retrieve the destination long URL.
     * </p>
     *
     * @param shortURL Unique alphanumeric key assigned to the short URL
     * @return {@link Optional} containing the matched {@link UrlEntity}, or empty if not found
     */
    Optional<UrlEntity> findByShortURL(String shortURL);
}
