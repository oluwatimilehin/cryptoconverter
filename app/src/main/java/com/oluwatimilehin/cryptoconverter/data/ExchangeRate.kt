package com.oluwatimilehin.cryptoconverter.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Oluwatimilehin on 19/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class ExchangeRate{

    @SerializedName("ETH")
    var ethRates: HashMap<String, Double> = HashMap()

    @SerializedName("BTC")
    var btcRates: HashMap<String, Double> = HashMap()
}