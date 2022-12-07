package com.roland.solva.util.Exception;

/**
 * @author Roland Pilpani 04.12.2022
 */
public class CurrencyRatesNotFoundedException extends RuntimeException {
    public CurrencyRatesNotFoundedException(String message) {
        super(message);
    }
}
