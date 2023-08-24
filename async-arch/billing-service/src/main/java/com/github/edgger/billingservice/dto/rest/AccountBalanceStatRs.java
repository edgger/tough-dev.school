package com.github.edgger.billingservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceStatRs {
    private long currentBalance;
    private List<AuditInfo> audit;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuditInfo {
        private ZonedDateTime dateTime;
        private long change;
        private String description;
    }
}
