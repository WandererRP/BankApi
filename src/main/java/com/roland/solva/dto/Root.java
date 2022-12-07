package com.roland.solva.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Root {
    USDKZT UsdKzt;
    RUBUSD RubUsd;

    @JsonProperty("USD/KZT")
    public USDKZT getUSDKZT() {
        return this.UsdKzt;
    }

    public void setUSDKZT(USDKZT uSDKZT) {
        this.UsdKzt = uSDKZT;
    }

    @JsonProperty("RUB/USD")
    public RUBUSD getRUBUSD() {
        return this.RubUsd;
    }

    public void setRUBUSD(RUBUSD rUBUSD) {
        this.RubUsd = rUBUSD;
    }
}
