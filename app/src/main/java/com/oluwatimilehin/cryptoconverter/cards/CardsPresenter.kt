package com.oluwatimilehin.cryptoconverter.cards

import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.data.Currency
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
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
        val cryptoApi: CryptoCompareService = CryptoCompareService.create()
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

