package com.oluwatimilehin.cryptoconverter.cards

import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.BasePresenter
import com.oluwatimilehin.cryptoconverter.data.Card
import com.oluwatimilehin.cryptoconverter.data.Constants
import com.oluwatimilehin.cryptoconverter.data.Currency
import com.oluwatimilehin.cryptoconverter.data.ExchangeRate
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers


/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter : CardsContract.Presenter, BasePresenter(){
    override fun addNewCard() {
        cardsView.showAddCard()
    }

    override fun loadCurrencies() {
        val cryptoApi: CryptoCompareService = CryptoCompareService.create()

        disposables.add(currencyDao.checkIfCurrenciesExist()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .map { currencies ->
                    if (currencies.isEmpty()) {
                        cardsView.showEmptyCurrenciesError()
                    } else {
                        cardsView.currenciesExist()
                    }

                }
                .map {
                    currencyDao.getAllCurrencies()
                            .subscribeOn(scheduler)
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { currencies ->
                                if (!currencies.isEmpty()) {
                                    cardsView.currenciesExist()
                                    updateCards()
                                            .subscribe()
                                }
                            }
                            .subscribe()
                }
                .map {
                    cryptoApi.getRates(Constants.currenciesString)
                            .subscribeOn(scheduler)
                            .doOnError { cardsView.showApiCallError() }
                            .flatMap { rates: ExchangeRate ->
                                val combinedList: MutableList<Currency> = ArrayList()

                                combinedList.addAll(createCurrencyObjects("BTC", rates.btcRates))
                                combinedList.addAll(createCurrencyObjects("ETH", rates.ethRates))

                                return@flatMap Single.just(combinedList)
                            }
                            .map { result -> saveDataInDb(result) }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                cardsView.onDatabaseUpdateSuccess()
                            }, { e -> e.printStackTrace() })
                }
                .subscribe())

    }

    override fun loadCards() {
        disposables.add(cardDao.getAllCards()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cards ->
                    cardsView.updateRecyclerView(cards)
                }, {
                    cardsView.showEmptyCardsError()
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
                        cardsView.updateRecyclerView(cards)
                    }
                }
                .doOnError { cardsView.showEmptyCardsError() }
    }

    override fun attachView(view: CardsContract.View) {
        this.cardsView = view
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

