package com.oluwatimilehin.cryptoconverter.data.repositories

import com.oluwatimilehin.cryptoconverter.data.local.LocalCurrencyDataManager
import com.oluwatimilehin.cryptoconverter.data.models.Currency
import com.oluwatimilehin.cryptoconverter.data.models.ExchangeRate
import com.oluwatimilehin.cryptoconverter.data.remote.RemoteCurrencyDataSource
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 14/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CurrencyRepository @Inject constructor(private val remoteCurrencyDataSource:
                                             RemoteCurrencyDataSource, private val localCurrencyDataManager: LocalCurrencyDataManager) {

    fun checkIfCurrenciesExist(): Single<List<Currency>> {
        return localCurrencyDataManager.getCurrenciesAsSingle()
                .filter({ it.isNotEmpty() })
                .toObservable()
                .singleOrError()
    }


    fun getAmountValue(from: String, to: String): Single<Double> {
        return localCurrencyDataManager.getValue(from, to)
    }


    fun loadCurrencies(): Flowable<MutableList<Currency>> {
        return remoteCurrencyDataSource.loadData()
                .flatMap { rates: ExchangeRate ->
                    val combinedList: MutableList<Currency> = ArrayList()
                    combinedList.addAll(createCurrencyObjects("BTC", rates.btcRates))
                    combinedList.addAll(createCurrencyObjects("ETH", rates.ethRates))

                    return@flatMap Single.just(combinedList)
                }
                .doOnSuccess { localCurrencyDataManager.saveCurrencies(it) }
                .toFlowable()

    }

    fun getObservableCurrencies(): Flowable<List<Currency>> = localCurrencyDataManager.getCurrenciesAsFlowable()


    private fun createCurrencyObjects(from: String, map: HashMap<String, Double>): List<Currency> {
        val list = ArrayList<Currency>(0)

        for (key in map.keys) {
            val amount: Double? = map[key]
            list.add(Currency(0, from, key, amount!!))
        }

        return list
    }

}