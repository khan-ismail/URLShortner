package com.zerotoismail.shorturlservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app")
public class AppConfig {

    // Spring automatically maps 'short_url_key_upper' to 'shortUrlKeyUpper'
    private String baseUrl;
    private String shortUrlKeyUpper;
    private String shortUrlKeyLower;
    private String shortUrlKeyNum;
    private String shortUrlKeySec;
    private int shortUrlLength;
}