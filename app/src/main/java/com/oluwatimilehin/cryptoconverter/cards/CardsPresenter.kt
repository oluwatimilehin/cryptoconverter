package com.oluwatimilehin.cryptoconverter.cards

import android.text.TextUtils
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.data.Currency
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter : CardsContract.Presenter {

    lateinit var view: CardsContract.View;
    val disposables: CompositeDisposable = CompositeDisposable();

    override fun attachView(view: CardsContract.View) {
        this.view = view
        loadDataFromApi()
    }


    override fun onDestroy() {
        disposables.clear()
    }

    override fun loadDataFromApi() {
        val currencies = arrayOf(ExtendedCurrency.CURRENCIES)
        val cryptoApi: CryptoCompareService = CryptoCompareService.create()
        val countries: String = TextUtils.join(",", currencies)

        val btcRates: Observable<List<Currency>> = cryptoApi.getBTCRates(countries)
                .flatMap { result: HashMap<String, Double> ->
                    return@flatMap Observable.just(createCurrencyObjects
                    ("BTC",
                            result))
                }

        val ethRates: Observable<List<Currency>> = cryptoApi.getETHRates(countries)
                .flatMap { result: HashMap<String, Double> ->
                    return@flatMap Observable.just(createCurrencyObjects("ETH", result))
                }

        val apiCall: Disposable = Observable.concat(ethRates, btcRates)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    values: List<Currency> ->
                    run {
                        App.database?.currencyDao()?.insertAllCurrencies(values)
                    }
                }
                .doOnError { e ->  }
                .subscribe();

        disposables.add(apiCall)
    }

    fun createCurrencyObjects(from: String, map: HashMap<String, Double>): List<Currency> {
        val list = ArrayList<Currency>(0)

        for (key in map.keys) {
            val amount: Double? = map.get(key)
            list.add(Currency(from, key, amount!!))
        }

        return list;
    }

    override fun saveDataInDb() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadDataFromDb() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

