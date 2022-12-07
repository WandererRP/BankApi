package com.roland.solva.models;

import com.roland.solva.enums.CurrencyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Roland Pilpani 03.12.2022
 */
@Entity
@Table(name = "month_limit")
@Getter
@Setter
public class MonthLimit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "account_id",updatable = false, insertable = false)
    private long accountId;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "limit_sum")
    private double limitSum;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "datetime")
    ZonedDateTime dateTime;

    public MonthLimit() {
    }
}
