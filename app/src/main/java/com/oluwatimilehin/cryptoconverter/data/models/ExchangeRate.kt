package com.oluwatimilehin.cryptoconverter.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Oluwatimilehin on 19/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
data class ExchangeRate(
        @SerializedName("ETH")
        val ethRates: HashMap<String, Double>,

        @SerializedName("BTC")
        val btcRates: HashMap<String, Double>,

        @SerializedName("LTC")
        val ltcRates: HashMap<String, Double>,

        @SerializedName("BH")
        val bhRates: HashMap<String, Double>
)

