package com.github.edgger.taskmanagerservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingCycle {
    private LocalDate date;
    private TransactionInfo transaction;
}
