package com.roland.solva.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.roland.solva.models.MonthLimit} entity
 */

public class MonthLimitDto implements Serializable {

    @NotNull(message = "Client's bank account should not be empty")
    @Max(value = 9999999999L, message = "Incorrect Client's bank account")
    @Min(value = 1L, message = "Incorrect Client's bank account")
    @ApiModelProperty(value = "Account id [10 digits]", required = true, example = "1200000123")
    private long accountId;

    @NotNull(message = "Limit sum should not be empty")
    @DecimalMin(value = "0.00", message = "Limit sum should be positive")
    @ApiModelProperty(value = "New limit sum in USD", required = true, example = "1000.45")
    private double limitSumUsd;

    public MonthLimitDto() {
    }



    public long getAccountId() {
        return accountId;
    }

    @JsonSetter("account_id")
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getLimitSumUsd() {
        return limitSumUsd;
    }

    @JsonSetter("limit_sum_usd")
    public void setLimitSumUsd(double limitSumUsd) {
        this.limitSumUsd = limitSumUsd;
    }
}