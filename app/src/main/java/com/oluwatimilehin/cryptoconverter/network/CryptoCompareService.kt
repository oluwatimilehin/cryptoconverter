package com.oluwatimilehin.cryptoconverter.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Oluwatimilehin on 12/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
interface CryptoCompareService {
    @GET("data/pricemulti?fsyms=BTC,ETH")
    fun getETHRates(@Query("tsyms") tsyms : String) : Single<HashMap<String, Double>>


    /**
     * Companion object to create the CryptoCompareService
     */
    companion object Factory {
        fun create() : CryptoCompareService{
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://min-api.cryptocompare.com/")
                    .build()

            return  retrofit.create(CryptoCompareService::class.java)
        }
    }
}