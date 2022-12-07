package com.roland.solva.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class CurrencyDto {
    private String symbol;
    private boolean is_market_open;
    private Date datetime;
    private String open;
    private double high;
    private double low;
    private double close;
    private double previous_close;


    @JsonProperty("symbol")
    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    @JsonProperty("datetime")
    public Date getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }


    @JsonProperty("open")
    public String getOpen() {
        return this.open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
    @JsonProperty("high")
    public double getHigh() {
        return this.high;
    }

    public void setHigh(double high) {
        this.high = high;
    }


    @JsonProperty("low")
    public double getLow() {
        return this.low;
    }

    public void setLow(double low) {
        this.low = low;
    }


    @JsonProperty("close")
    public double getClose() {
        return this.close;
    }

    public void setClose(double close) {
        this.close = close;
    }


    @JsonProperty("previous_close")
    public double getPrevious_close() {
        return this.previous_close;
    }

    public void setPrevious_close(double previous_close) {
        this.previous_close = previous_close;
    }


    @JsonProperty("is_market_open")
    public boolean getIs_market_open() {
        return this.is_market_open;
    }

    public void setIs_market_open(boolean is_market_open) {
        this.is_market_open = is_market_open;
    }

}
