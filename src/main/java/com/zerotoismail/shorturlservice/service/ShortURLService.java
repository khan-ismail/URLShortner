package com.zerotoismail.shorturlservice.service;

import com.zerotoismail.shorturlservice.dto.UrlRequest;

public interface ShortURLService {
    String convertLongUrlToShort(UrlRequest longUrl);
    String convertShortUrlToLong(String shortUrl);
}
