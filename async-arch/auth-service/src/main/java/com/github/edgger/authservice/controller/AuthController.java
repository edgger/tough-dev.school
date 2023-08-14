package com.github.edgger.authservice.controller;

import com.github.edgger.authservice.config.security.JwtHelper;
import com.github.edgger.authservice.config.security.SecurityConfiguration;
import com.github.edgger.authservice.dto.rest.LoginRs;
import com.github.edgger.authservice.entity.Account;
import com.github.edgger.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final JwtHelper jwtHelper;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public LoginRs login(
            @RequestParam String username,
            @RequestParam String password) {

        Account account = accountService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (passwordEncoder.matches(password, account.getPassword())) {
            Map<String, String> claims = new HashMap<>();
            claims.put("username", username);

            String authorities = account.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
            claims.put(SecurityConfiguration.AUTHORITIES_CLAIM_NAME, authorities);
            claims.put("userId", account.getId().toString());

            String jwt = jwtHelper.createJwtForClaims(username, claims);
            return new LoginRs(jwt);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
}
