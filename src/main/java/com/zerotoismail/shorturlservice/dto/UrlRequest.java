package com.zerotoismail.shorturlservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest {
    private String longUrl;
}