package com.nextu.storage.payloads.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtResponse {
    private final String token;
    private final String id;
    private final String email;
    private static final String TYPE = "Bearer";

}
