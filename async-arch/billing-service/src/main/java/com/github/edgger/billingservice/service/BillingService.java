package com.github.edgger.billingservice.service;

import com.github.edgger.billingservice.dto.rest.AccountBalanceStatRs;
import com.github.edgger.billingservice.dto.rest.DayBalanceInfoRs;
import com.github.edgger.billingservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BillingService {

    private AccountRepository accountRepository;

    public DayBalanceInfoRs getTodayInfo() {
        return null;
    }

    public List<DayBalanceInfoRs> getFullInfo() {
        return null;
    }

    public AccountBalanceStatRs getAccountStat(String userId) {
        return null;
    }
}
