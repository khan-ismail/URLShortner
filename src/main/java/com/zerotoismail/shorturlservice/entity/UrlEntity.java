package com.zerotoismail.shorturlservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Table(name = "urlTbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String longURL;

    @Column(length = 20, nullable = false, unique = true)
    private String shortURL;
}