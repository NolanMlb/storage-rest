package com.nextu.storage.controllers;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;

@RestController
@RequestMapping("/api/ratelimiter")
@RequiredArgsConstructor
public class RateLimiterController {

    private final Bucket bucket;

    public RateLimiterController() {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }
    @ResponseBody
    @RequestMapping(value = "/test2")
    public ResponseEntity<String> handle2 () {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok("new data");
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
