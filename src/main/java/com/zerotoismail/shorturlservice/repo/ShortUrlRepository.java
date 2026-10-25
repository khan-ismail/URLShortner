package com.zerotoismail.shorturlservice.repo;

import com.zerotoismail.shorturlservice.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends JpaRepository<UrlEntity, Integer> {

    // Spring will automatically generate this lookup query for your redirect logic!
    Optional<UrlEntity> findByShortURL(String shortURL);
}
