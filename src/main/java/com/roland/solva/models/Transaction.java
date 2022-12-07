package com.roland.solva.models;

import com.roland.solva.enums.CurrencyType;
import com.roland.solva.enums.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Roland Pilpani 03.12.2022
 */
@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "account_from", insertable = false, updatable = false)
    private long accountFrom;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "account_from")
    private Account userAccount;

    @Column(name = "account_to")
    private long accountTo;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "sum")
    private double sum;

    @Column(name = "sum_usd")
    private double sumUsd;


    @Column(name = "datetime")
    private ZonedDateTime dateTime;

    @Column(name = "expense_category")
    @Enumerated(EnumType.STRING)
    private ExpenseCategory expenseCategory;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    public Transaction() {
    }
}
