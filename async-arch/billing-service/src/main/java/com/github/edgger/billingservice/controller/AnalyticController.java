package com.github.edgger.billingservice.controller;

import com.github.edgger.billingservice.dto.rest.DayAnalyticInfoRs;
import com.github.edgger.billingservice.service.AnalyticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/analytic")
public class AnalyticController {

    private final AnalyticService analyticService;

    @GetMapping("/today")
    @PreAuthorize("hasAuthority(T(com.github.edgger.billingservice.entity.AccountRole).ADMIN.toString())")
    public ResponseEntity<DayAnalyticInfoRs> getTodayInfo() {
        DayAnalyticInfoRs todayInfo = analyticService.getTodayInfo();
        return ResponseEntity.ok(todayInfo);
    }

}
