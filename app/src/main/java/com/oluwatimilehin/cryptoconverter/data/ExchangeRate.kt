package com.oluwatimilehin.cryptoconverter.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Oluwatimilehin on 19/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
data class ExchangeRate(
        @SerializedName("ETH")
        val ethRates: HashMap<String, Double>,

        @SerializedName("BTC")
        val btcRates: HashMap<String, Double>
)

