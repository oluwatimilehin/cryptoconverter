package com.oluwatimilehin.cryptoconverter.data.remote

import com.oluwatimilehin.cryptoconverter.utils.Constants
import com.oluwatimilehin.cryptoconverter.data.models.ExchangeRate
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 24/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class RemoteCurrencyDataSource @Inject constructor(private val apiService: CryptoCompareService){
     fun loadData(): Single<ExchangeRate> = apiService.getRates(Constants.currenciesString)
}