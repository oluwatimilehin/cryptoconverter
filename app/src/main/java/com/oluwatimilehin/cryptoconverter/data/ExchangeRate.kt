package com.oluwatimilehin.cryptoconverter.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Oluwatimilehin on 19/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ExchangeRate{

    @SerializedName("ETH")
    lateinit var ethRates: HashMap<String, Double>

    @SerializedName("BTC")
    lateinit var btcRates: HashMap<String, Double>
}