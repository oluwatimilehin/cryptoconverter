package com.oluwatimilehin.cryptoconverter.data;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

public class ExchangeRate {

    @SerializedName("ETH")
    private HashMap<String, Double> ETHRates;

    public HashMap<String, Double> getETHRates() {
        return ETHRates;
    }

    @SerializedName("BTC")
    private HashMap<String, Double> BTCRates;

    public HashMap<String, Double> getBTCRates() {
        return ETHRates;
    }

}
