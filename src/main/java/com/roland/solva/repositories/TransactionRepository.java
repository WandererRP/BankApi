package com.roland.solva.repositories;

import com.roland.solva.models.Account;
import com.roland.solva.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByDateTimeBetweenAndAccountFrom(ZonedDateTime after, ZonedDateTime before, long accountFrom);

    List<Transaction> findAllByAccountFromAndLimitExceededTrue(long accountFrom);

    List<Transaction> findAllByAccountFrom(long accountFrom);




}