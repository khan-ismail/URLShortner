package com.zerotoismail.shorturlservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {
    private int statusCode;
    private String message;
    private long timestamp;
}
