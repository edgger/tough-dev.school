package com.github.edgger.authservice.service;

import com.github.edgger.authservice.dto.RegisterAccountRq;
import com.github.edgger.authservice.entity.Account;
import com.github.edgger.authservice.entity.AccountRoles;
import com.github.edgger.authservice.repository.AccountRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducer;

    @PostConstruct
    protected void addDefaultAccounts(){

    }

    public void addNewAccount(RegisterAccountRq rq) {
        Account account = new Account(null,
                rq.getUsername(),
                passwordEncoder.encode(rq.getPassword()),
                AccountRoles.WORKER);
        account = accountRepository.save(account);
        kafkaProducer.sendAccountCreatedEvent(account.getId().toString());
    }

    @Transactional
    public void changeRole(UUID accountId, AccountRoles role) {
        accountRepository.findById(accountId)
                .ifPresentOrElse(account -> account.setRole(role),
                        () -> {
                            throw new EntityNotFoundException(accountId.toString());
                        });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username);
    }
}
