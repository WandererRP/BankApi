package com.roland.solva.services;

import com.roland.solva.dto.TransactionDto;
import com.roland.solva.enums.CurrencyType;
import com.roland.solva.models.Account;
import com.roland.solva.models.CurrencyRates;
import com.roland.solva.models.MonthLimit;
import com.roland.solva.models.Transaction;
import com.roland.solva.repositories.AccountRepository;
import com.roland.solva.repositories.CurrencyRatesRepository;
import com.roland.solva.repositories.MonthLimitRepository;
import com.roland.solva.repositories.TransactionRepository;
import com.roland.solva.util.Exception.CurrencyRatesNotFoundedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Roland Pilpani 04.12.2022
 */
@Service
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final MonthLimitRepository monthLimitRepository;
    private final CurrencyRatesRepository currencyRatesRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository,
                              MonthLimitRepository monthLimitRepository, CurrencyRatesRepository currencyRatesRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.monthLimitRepository = monthLimitRepository;
        this.currencyRatesRepository = currencyRatesRepository;
    }

    @Transactional
    public void save(TransactionDto transactionDto) throws CurrencyRatesNotFoundedException {
        Transaction transaction = convertToTransaction(transactionDto);
        transaction = transactionRepository.save(transaction);
    }

    public List<TransactionDto> getAllExceeded(long id) {

        List<TransactionDto> answer = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findAllByAccountFromAndLimitExceededTrue(id);

        transactions.forEach(transaction -> {
            MonthLimit monthLimit = monthLimitRepository.findTopByAccountIdAndDateTimeBeforeOrderByDateTimeDesc(transaction.getAccountFrom(), transaction.getDateTime());
            if(monthLimit==null){
                monthLimit = new MonthLimit();
                monthLimit.setLimitSum(0);
                monthLimit.setDateTime(null);
                monthLimit.setCurrency(CurrencyType.USD);
            }
            TransactionDto transactionDto = convertToTransactionDto(transaction, monthLimit);
            answer.add(transactionDto);
        });

        return answer;
    }

    private TransactionDto convertToTransactionDto(Transaction transaction, MonthLimit monthLimit) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setAccountFrom(transaction.getAccountFrom());
        transactionDto.setAccountTo(transaction.getAccountTo());
        transactionDto.setSum(transaction.getSum());
        transactionDto.setCurrency(transaction.getCurrency());
        transactionDto.setExpenseCategory(transaction.getExpenseCategory());
        transactionDto.setDateTime(transaction.getDateTime());
        transactionDto.setLimitSum(monthLimit.getLimitSum());
        transactionDto.setLimitCurrency(monthLimit.getCurrency());
        transactionDto.setLimitDateTime(monthLimit.getDateTime());

        return transactionDto;




    }


    private Transaction convertToTransaction(TransactionDto transactionDto) throws CurrencyRatesNotFoundedException {
        Transaction transaction = new Transaction();

        Optional<Account> optionalAccount = accountRepository.findById(transactionDto.getAccountFrom());
        Account account;
        if(optionalAccount.isEmpty()){
            account = new Account();
            account.setId(transactionDto.getAccountFrom());
        }
        else account = optionalAccount.get();


        transaction.setUserAccount(account);
        transaction.setAccountTo(transactionDto.getAccountTo());
        transaction.setCurrency(transactionDto.getCurrency());
        transaction.setSum(transactionDto.getSumPrecise());


        Optional<CurrencyRates> optionalCurrencyRates = currencyRatesRepository.findById(transaction.getCurrency());
        if(optionalCurrencyRates.isEmpty()){
            throw new CurrencyRatesNotFoundedException("");
        }

        transaction.setSumUsd(transaction.getSum() * optionalCurrencyRates.get().getRate());
        transaction.setExpenseCategory(transactionDto.getExpenseCategory());
        transaction.setDateTime(transactionDto.getDateTime());

        transaction.setLimitExceeded(checkLimit(transaction.getDateTime(), account, transaction.getSumUsd()));

        return transaction;
    }

    private boolean checkLimit(ZonedDateTime dateTime, Account account, double sumUsd) {
        MonthLimit monthLimit = monthLimitRepository.findTopByAccountIdAndDateTimeBeforeOrderByDateTimeDesc(account.getId(), dateTime);
        if(monthLimit==null){
            monthLimit = new MonthLimit();
            monthLimit.setLimitSum(0);
            monthLimit.setDateTime(null);
            monthLimit.setCurrency(CurrencyType.USD);
        }

        ZonedDateTime start = ZonedDateTime.of(dateTime.getYear(), dateTime.getMonth().getValue(), 1, 0, 0, 0,0, dateTime.getZone());
        List<Transaction> transactions = transactionRepository.findAllByDateTimeBetweenAndAccountFrom(start, dateTime, account.getId());
        double totalSumUsdOfPreviousTrans = transactions.stream().map(Transaction::getSumUsd).reduce(0.0, Double::sum);

        return totalSumUsdOfPreviousTrans + sumUsd > monthLimit.getLimitSum();
    }

}
