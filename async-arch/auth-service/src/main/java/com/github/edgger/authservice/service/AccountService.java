package com.github.edgger.authservice.service;

import com.github.edgger.authservice.dto.kafka.AccountCreatedEvt;
import com.github.edgger.authservice.dto.kafka.AccountRoleChangedEvt;
import com.github.edgger.authservice.dto.rest.RegisterWorkerAccountRq;
import com.github.edgger.authservice.entity.Account;
import com.github.edgger.authservice.entity.AccountRole;
import com.github.edgger.authservice.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducer;

    @Transactional
    public void addNewWorkerAccount(RegisterWorkerAccountRq rq) {
        Account account = new Account(null,
                rq.getUsername(),
                passwordEncoder.encode(rq.getPassword()),
                AccountRole.WORKER);
        account = accountRepository.save(account);
        AccountCreatedEvt evt = new AccountCreatedEvt(account.getId().toString(), account.getRole());
        kafkaProducer.sendAccountCreatedEvent(evt);
    }

    @Transactional
    public void changeRole(UUID accountId, AccountRole role) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(accountId.toString()));
        account.setRole(role);
        AccountRoleChangedEvt evt = new AccountRoleChangedEvt(account.getId().toString(), account.getRole());
        kafkaProducer.sendAccountRoleChangedEvent(evt);
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
