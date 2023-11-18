package com.github.edgger.billingservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayBalanceInfoRs {
    private LocalDate date;
    private long balance;
}
