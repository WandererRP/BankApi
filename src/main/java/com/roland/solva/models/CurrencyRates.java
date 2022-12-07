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
@Table(name = "currency_rates")
@Getter
@Setter
public class CurrencyRates {
    @Id
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyType currency;

    @Column(name = "rate")
    private double rate;

    @Column(name = "datetime")
    private ZonedDateTime dateTime;


    public CurrencyRates() {
    }
}
