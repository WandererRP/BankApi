package com.roland.solva.services;

import com.roland.solva.dto.MonthLimitDto;
import com.roland.solva.enums.CurrencyType;
import com.roland.solva.models.Account;
import com.roland.solva.models.MonthLimit;
import com.roland.solva.repositories.AccountRepository;
import com.roland.solva.repositories.MonthLimitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * @author Roland Pilpani 04.12.2022
 */
@Service
@Transactional(readOnly = true)
public class LimitService {
    private final MonthLimitRepository monthLimitRepository;
    private final AccountRepository accountRepository;


    @Autowired
    public LimitService(MonthLimitRepository monthLimitRepository, AccountRepository accountRepository) {
        this.monthLimitRepository = monthLimitRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void convertAndSave(MonthLimitDto monthLimitDto){
        MonthLimit newMonthLimit = new MonthLimit();

        Optional<Account> optionalAccount = accountRepository.findById(monthLimitDto.getAccountId());
        Account account;
        if(optionalAccount.isEmpty()){
            account = new Account();
            account.setId(monthLimitDto.getAccountId());
            account = accountRepository.save(account);
        }
        else account = optionalAccount.get();

        newMonthLimit.setAccount(account);
        newMonthLimit.setLimitSum(monthLimitDto.getLimitSumUsd());
        newMonthLimit.setCurrency(CurrencyType.USD);
        newMonthLimit.setDateTime(ZonedDateTime.now());
        monthLimitRepository.save(newMonthLimit);
    }
}
