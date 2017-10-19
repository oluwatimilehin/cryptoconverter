package com.oluwatimilehin.cryptoconverter.cards

import android.content.Context
import android.widget.Toast
import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.data.Constants
import com.oluwatimilehin.cryptoconverter.data.Currency
import com.oluwatimilehin.cryptoconverter.data.ExchangeRate
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter(paramContext: Context) : CardsContract.Presenter {

    lateinit var view: CardsContract.View;
    val disposables: CompositeDisposable = CompositeDisposable();
    val scheduler: Scheduler = Schedulers.io()
    val context = paramContext

    override fun attachView(view: CardsContract.View) {
        this.view = view
        loadDataFromApi()
    }


    override fun onDestroy() {
        disposables.clear()
    }

    override fun loadDataFromApi() {
        val cryptoApi: CryptoCompareService = CryptoCompareService.create()


        val apiCallDisposable: Disposable = cryptoApi.getRates(Constants.currenciesString)
                .subscribeOn(Schedulers.io())
                .doOnError{ e -> e.printStackTrace()}
                .flatMap { rates: ExchangeRate ->
                    val combinedList: MutableList<Currency> = ArrayList()

                    combinedList.addAll(createCurrencyObjects("BTC", rates.btcRates ))
                    combinedList.addAll(createCurrencyObjects("ETH", rates.ethRates))

                    return@flatMap  Single.just(combinedList)
                }
                .map { result -> saveDataInDb(result)}
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(context, "Saved to database", Toast
                        .LENGTH_SHORT).show()}, { e -> e.printStackTrace()} )


        disposables.add(apiCallDisposable)

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

