package com.oluwatimilehin.cryptoconverter.data;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */

public class ETHExchangeRate {

    @SerializedName("ETH")
    private HashMap<String, Double> exchangeRates;

    public HashMap<String, Double> getExchangeRates() {
        return exchangeRates;
    }
}
