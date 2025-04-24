package com.example.Testovoe.Service;

import com.example.Testovoe.Entity.Account;
import com.example.Testovoe.Repository.Account_repository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class Account_balance_service {

    private final Account_repository accountRepository;

    private BigDecimal initialBalance = BigDecimal.valueOf(100);

    public Account_balance_service(Account_repository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void updateAccountBalances() {
        List<Account> accounts = accountRepository.findAll();

        for (Account account : accounts) {
            BigDecimal currentBalance = account.getBalance();

            BigDecimal increaseAmount = currentBalance.multiply(new BigDecimal("0.10"));

            BigDecimal maxBalance = initialBalance.multiply(new BigDecimal("2.07"));

            BigDecimal newBalance = currentBalance.add(increaseAmount);

            if (newBalance.compareTo(maxBalance) > 0) {
                newBalance = maxBalance;
            }

            newBalance = newBalance.setScale(2, RoundingMode.HALF_UP);

            account.setBalance(newBalance);
            accountRepository.save(account);
        }
    }
    }
