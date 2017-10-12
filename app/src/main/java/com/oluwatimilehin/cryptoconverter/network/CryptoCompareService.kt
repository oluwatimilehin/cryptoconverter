package com.oluwatimilehin.cryptoconverter.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface CryptoCompareService {
    @GET("data/price?fsym=ETH")
    fun getETHRates(@Query("tsyms") tsyms : String) : Single<HashMap<String, Double>>

    @GET("data/price?fsym=BTC")
    fun getBTCRates(@Query("tsyms") tsyms: String) : Single<HashMap<String, Double>>

}