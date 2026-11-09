package com.zerotoismail.shorturlservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {

    private final StringRedisTemplate redisTemplate;
    private static final String KEY_NAMESPACE = "ismail:";

    @PostMapping("/{key}")
    public ResponseEntity<Void> setRedisMessage(@PathVariable String key, @RequestBody String message) {
        redisTemplate.opsForValue().set(KEY_NAMESPACE+ key, message);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> getRedisMessage(@PathVariable String key) {
        String message = redisTemplate.opsForValue().get(KEY_NAMESPACE+ key);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deleteRedisMessage(@PathVariable String key) {
        Boolean deleted = redisTemplate.delete(KEY_NAMESPACE+ key);
        if (Boolean.FALSE.equals(deleted)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
