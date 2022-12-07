package com.roland.solva.dto;


import com.fasterxml.jackson.annotation.*;
import com.roland.solva.enums.CurrencyType;
import com.roland.solva.enums.ExpenseCategory;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;

/**
 * A DTO for the {@link com.roland.solva.models.Transaction} entity
 */
public class TransactionDto implements Serializable {


    @NotNull(message = "Client's bank account should not be empty")
    @Max(value = 9999999999L, message = "Incorrect Client's bank account")
    @Min(value = 1L, message = "Incorrect Client's bank account")
    @ApiModelProperty(value = "Client's bank account [10 digits]", name = "account_from", required = true, example = "1200000123")
    private long accountFrom;

    @NotNull(message = "Counterpart's bank account should not be empty")
    @Max(value = 9999999999L, message = "Incorrect Counterpart's bank account")
    @Min(value = 1L, message = "Incorrect Counterpart's bank account")
    @ApiModelProperty(value = "Counterpart's bank account [10 digits]", required = true, example = "9999999123")
    private long accountTo;


    @NotNull(message = "Currency shortname should not be empty. KZT or RUB")
    @ApiModelProperty(value = "Currency shortname",
            required = true, example = "KZT")
    private CurrencyType currency;


    @NotNull(message = "Transfer amount should not be empty")
    @DecimalMin(value = "0.00", message = "Transfer amount should be positive")
    @ApiModelProperty(value = "Transfer amount",
            required = true, example = "10000.35")
    private double sum;

    @NotNull(message = "DateTime should not be empty")
    @ApiModelProperty(value = "DateTime of a transaction in the format yyyy-MM-dd HH:mm:ssx",
            required = true, dataType = "string",
            example = "2022-12-05 11:30:20+06")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssx")
    private ZonedDateTime dateTime;

    @NotNull(message = "Two options available: SERVICE,PRODUCT. Expense category should not be empty")
    @ApiModelProperty(value = "Expense category. Two options available: SERVICE,PRODUCT",
            required = true)
    private ExpenseCategory expenseCategory;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double limitSum;

    @ApiModelProperty(value = "Date when the limit was set in the format yyyy-MM-dd HH:mm:ssx", dataType = "String",     example = "2022-12-01 10:40:20+06" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssx")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ZonedDateTime limitDateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CurrencyType limitCurrency;

    public TransactionDto() {
    }


    @JsonGetter("account_from")
    public long getAccountFrom() {
        return accountFrom;
    }

    @JsonSetter("account_from")
    public void setAccountFrom(long accountFrom) {
        this.accountFrom = accountFrom;
    }

    @JsonGetter("account_to")
    public long getAccountTo() {
        return accountTo;
    }

    @JsonSetter("account_to")
    public void setAccountTo(long accountTo) {
        this.accountTo = accountTo;
    }

    @JsonGetter("currency_shortname")
    public CurrencyType getCurrency() {
        return currency;
    }

    @JsonSetter("currency_shortname")
    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    @JsonGetter("sum")
    public double getSum() {
        return round(sum, 2);
    }

    @JsonIgnore
    public double getSumPrecise() {
        return sum;
    }


    @JsonSetter("sum")
    public void setSum(double sum) {
        this.sum = sum;
    }

    @JsonGetter("datetime")
    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    @JsonSetter("datetime")
    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @JsonGetter("expense_category")
    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    @JsonSetter("expense_category")
    public void setExpenseCategory(ExpenseCategory expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    @JsonGetter("limit_sum")
    public double getLimitSum() {
        return round(limitSum, 2);
    }

    public void setLimitSum(double limitSum) {
        this.limitSum = limitSum;
    }

    @JsonGetter("limit_datetime")
    public ZonedDateTime getLimitDateTime() {
        return limitDateTime;
    }

    public void setLimitDateTime(ZonedDateTime limitDateTime) {
        this.limitDateTime = limitDateTime;
    }

    @JsonGetter("limit_currency_shortname")
    public CurrencyType getLimitCurrency() {
        return limitCurrency;
    }

    public void setLimitCurrency(CurrencyType limitCurrency) {
        this.limitCurrency = limitCurrency;
    }


    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}