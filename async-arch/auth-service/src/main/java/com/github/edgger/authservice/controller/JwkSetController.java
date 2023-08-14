package com.github.edgger.authservice.controller;

import com.nimbusds.jose.jwk.JWKSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class JwkSetController {

    private final JWKSet jwkSet;

    @GetMapping("/auth/jwks.json")
    public Map<String, Object> keys() {
        log.info("JWKS requested");
        return jwkSet.toJSONObject();
    }
}
