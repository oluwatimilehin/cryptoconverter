package com.oluwatimilehin.cryptoconverter.cards

import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.data.*
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter : CardsContract.Presenter {

    override fun loadCurrencies() {
        val cryptoApi: CryptoCompareService = CryptoCompareService.create()

        currencyDao.checkIfCurrenciesExist()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .map { currencies ->
                    if (currencies.isEmpty()) {
                        view.showEmptyCurrenciesError()
                    } else {
                        view.currenciesExist()
                    }

                }
                .map {
                    currencyDao.getAllCurrencies()
                            .subscribeOn(scheduler)
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { currencies ->
                                if(!currencies.isEmpty()) {
                                    view.currenciesExist()
                                    updateCards()
                                            .subscribe()
                                }
                            }
                            .subscribe()
                }
                .map {
                    cryptoApi.getRates(Constants.currenciesString)
                            .subscribeOn(scheduler)
                            .doOnError { view.showApiCallError() }
                            .flatMap { rates: ExchangeRate ->
                                val combinedList: MutableList<Currency> = ArrayList()

                                combinedList.addAll(createCurrencyObjects("BTC", rates.btcRates))
                                combinedList.addAll(createCurrencyObjects("ETH", rates.ethRates))

                                return@flatMap Single.just(combinedList)
                            }
                            .map { result -> saveDataInDb(result) }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                view.onDatabaseUpdateSuccess()
                            }, { e -> e.printStackTrace() })
                }
                .subscribe()

//        disposables.add(currencyDao.getAllCurrencies()
//                .subscribeOn(scheduler)
//                .map {
//                    updateCards()
//                            .subscribe()
//                }
//                .subscribe())


    }

    override fun loadCards() {
        disposables.add(cardDao.getAllCards()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cards ->
                    view.updateRecyclerView(cards)
                }, {
                    view.showEmptyCardsError()
                }))

    }

    fun updateCards(): Single<List<Card>> {
        return cardDao.getAllCards()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { cards ->
                    run {
                        for (card in cards) {
                            currencyDao.getConversionRate(card.from, card.to)
                                    .subscribeOn(scheduler)
                                    .subscribe({ amount ->
                                        cardDao.updateAmount(amount, card
                                                .from, card.to)
                                    }, { e -> e.printStackTrace() })
                        }
                        view.updateRecyclerView(cards)
                    }
                }
                .doOnError { view.showEmptyCardsError() }
    }

    lateinit var view: CardsContract.View;
    val disposables: CompositeDisposable = CompositeDisposable();
    val scheduler: Scheduler = Schedulers.single()
    lateinit var cardDao: CardDao
    lateinit var currencyDao: CurrencyDao

    override fun attachView(view: CardsContract.View) {
        this.view = view
        currencyDao = App.database.currencyDao()
        cardDao = App.database.cardDao()

        loadCurrencies()
    }


    override fun onDestroy() {
        disposables.clear()
    }

    private fun createCurrencyObjects(from: String, map: HashMap<String, Double>): List<Currency> {
        val list = ArrayList<Currency>(0)

        for (key in map.keys) {
            val amount: Double? = map.get(key)
            list.add(Currency(0, from, key, amount!!))
        }

        return list
    }

    fun saveDataInDb(list: List<Currency>) {
        currencyDao.insertAllCurrencies(list)
    }

}

