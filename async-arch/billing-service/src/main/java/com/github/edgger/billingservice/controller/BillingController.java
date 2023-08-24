package com.github.edgger.billingservice.controller;

import com.github.edgger.billingservice.dto.rest.AccountBalanceStatRs;
import com.github.edgger.billingservice.dto.rest.DayBalanceInfoRs;
import com.github.edgger.billingservice.service.BillingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/billing-info")
public class BillingController {

    private final BillingService billingService;

    @GetMapping("/today")
    @PreAuthorize("hasAnyAuthority(T(com.github.edgger.billingservice.entity.AccountRole).MANAGER.toString()," +
            "T(com.github.edgger.billingservice.entity.AccountRole).ADMIN.toString())")
    public ResponseEntity<DayBalanceInfoRs> getTodayInfo() {
        DayBalanceInfoRs todayInfo = billingService.getTodayInfo();
        return ResponseEntity.ok(todayInfo);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority(T(com.github.edgger.billingservice.entity.AccountRole).MANAGER.toString()," +
            "T(com.github.edgger.billingservice.entity.AccountRole).ADMIN.toString())")
    public ResponseEntity<List<DayBalanceInfoRs>> getAll() {
        List<DayBalanceInfoRs> fullStatistic = billingService.getFullInfo();
        return ResponseEntity.ok(fullStatistic);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAuthority(T(com.github.edgger.billingservice.entity.AccountRole).WORKER.toString())")
    public ResponseEntity<AccountBalanceStatRs> getAccountStat(Authentication authentication) {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        Map<String, Object> attributes = token.getTokenAttributes();
        String userId = attributes.get("userId").toString();
        AccountBalanceStatRs accountStat = billingService.getAccountStat(userId);
        return ResponseEntity.ok(accountStat);
    }


}
