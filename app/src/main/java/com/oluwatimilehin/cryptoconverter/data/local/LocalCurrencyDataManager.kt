package com.oluwatimilehin.cryptoconverter.data.local

import com.oluwatimilehin.cryptoconverter.data.daos.CurrencyDao
import com.oluwatimilehin.cryptoconverter.data.models.Currency
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 24/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class LocalCurrencyDataManager @Inject constructor(val currencyDao: CurrencyDao){
        fun getCurrenciesAsSingle(): Single<List<Currency>>{
            return currencyDao.checkIfCurrenciesExist()
        }

        fun saveCurrencies(currencies: List<Currency>){
            currencyDao.insertAllCurrencies(currencies)
        }

       fun getValue(from: String, to: String): Single<Double>{
           return currencyDao.getConversionRate(from, to)
       }

       fun getCurrenciesAsFlowable(): Flowable<List<Currency>> = currencyDao.getAllCurrencies()
}