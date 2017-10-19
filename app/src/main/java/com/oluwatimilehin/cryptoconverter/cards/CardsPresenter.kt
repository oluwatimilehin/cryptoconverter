package com.oluwatimilehin.cryptoconverter.cards

import android.text.TextUtils
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.data.Currency
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter : CardsContract.Presenter {

    lateinit var view: CardsContract.View;
    val disposables: CompositeDisposable = CompositeDisposable();
    val scheduler: Scheduler = Schedulers.io()

    override fun attachView(view: CardsContract.View) {
        this.view = view
        loadDataFromApi()
    }


    override fun onDestroy() {
        disposables.clear()
    }

    override fun loadDataFromApi() {
        val currencies = ExtendedCurrency.CURRENCIES

        val list: ArrayList<String> = ArrayList();

        for(item: ExtendedCurrency in currencies){
            list.add(item.code)
        }

        val cryptoApi: CryptoCompareService = CryptoCompareService.create()
        val countries: String = TextUtils.join(",", list)

        val btcRates: Single<List<Currency>> = cryptoApi.getBTCRates(countries)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { e -> e.printStackTrace() }
                .flatMap { result: HashMap<String, Double> ->
                    return@flatMap Single.just(createCurrencyObjects
                    ("BTC",
                            result))
                }


        val ethRates: Single<List<Currency>> = cryptoApi.getETHRates(countries)
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { result: HashMap<String, Double> ->
                    return@flatMap Single.just(createCurrencyObjects("ETH", result))
                }
                .doOnError { e -> e.printStackTrace() }


        val apiCall: Disposable = Single.zip(btcRates, ethRates, BiFunction { ethlist:
                                                                              List<Currency>,
                                                                              btcList:
                                                                              List<Currency> ->
            val list: ArrayList<Currency> = ArrayList();
            list.addAll(ethlist)
            list.addAll(btcList)
            return@BiFunction list
        })
                .doOnSuccess{list ->
                    saveDataInDb(list)}
                .doOnError{e -> e.printStackTrace()}
                .subscribe()

        disposables.add(apiCall)
    }

    fun createCurrencyObjects(from: String, map: HashMap<String, Double>): List<Currency> {
        val list = ArrayList<Currency>(0)

        for (key in map.keys) {
            val amount: Double? = map.get(key)
            list.add(Currency(0, from, key, amount!!))
        }

        return list;
    }

    override fun saveDataInDb(list: List<Currency>) {
        App.database?.currencyDao()?.insertAllCurrencies(list)
    }

    override fun loadDataFromDb() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

