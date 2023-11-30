package com.nextu.storage.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
@RestController
@RequestMapping("/api/cache")
@RequiredArgsConstructor
public class CacheController {

    @ResponseBody
    @RequestMapping(value = "/test2")
    public ResponseEntity<String> handle2 () {

        CacheControl cacheControl = CacheControl.maxAge(10, TimeUnit.SECONDS);

        String testBody = "<p>Response time: " + LocalDateTime.now() +
                "</p><a href=''>test2</a>";
        return ResponseEntity.ok()
                .cacheControl(cacheControl)
                .body(testBody);
    }
}
