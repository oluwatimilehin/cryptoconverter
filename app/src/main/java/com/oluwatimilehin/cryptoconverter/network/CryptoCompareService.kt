package com.oluwatimilehin.cryptoconverter.network

import com.oluwatimilehin.cryptoconverter.data.models.ExchangeRate
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface CryptoCompareService {
    @GET("data/pricemulti?fsyms=BTC,ETH,LTC,BH")
    fun getRates(@Query("tsyms") tsyms : String) : Single<ExchangeRate>


    /**
     * Companion object to create the CryptoCompareService
     */
    companion object Factory {
        fun create() : CryptoCompareService{

            return  CryptoCompareService.create()
        }
    }
}