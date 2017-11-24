package com.oluwatimilehin.cryptoconverter.cards

import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.BasePresenter
import com.oluwatimilehin.cryptoconverter.data.CardsRepository
import com.oluwatimilehin.cryptoconverter.data.Constants
import com.oluwatimilehin.cryptoconverter.data.CurrencyRepository
import com.oluwatimilehin.cryptoconverter.data.di.RunOn
import com.oluwatimilehin.cryptoconverter.data.models.Card
import com.oluwatimilehin.cryptoconverter.data.models.Currency
import com.oluwatimilehin.cryptoconverter.data.models.ExchangeRate
import com.oluwatimilehin.cryptoconverter.network.CryptoCompareService
import com.oluwatimilehin.cryptoconverter.utils.schedulers.SchedulerType
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter @Inject constructor(val cardsRepository: CardsRepository, val currencyRepository:
CurrencyRepository,val view: CardsContract.View, @RunOn(SchedulerType.IO) val ioScheduler:
Scheduler,@RunOn(SchedulerType.MAIN) val mainThread: Scheduler) :
        CardsContract.Presenter{

    lateinit var disposables: CompositeDisposable

    override fun attachView(connected: Boolean) {
        loadCurrencies(connected)
    }

    override fun deleteAllCards() {
        disposables.add(Completable.fromAction{ cardDao.deleteAllCards()}
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadCards() }
        )
    }

    override fun loadDetails(card: Card) {
        val from = card.from
        val to = card.to
        val amount = card.amount

        view.showConversionScreen(from, to, amount)
    }

    override fun deleteCard(card: Card) {
        disposables.add(Completable.fromAction { cardDao.deleteCard(card.from, card.to) }
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showCardDeleted()
                    loadCards()
                }))
    }

    override fun addNewCard() {
        view.showAddCard()
    }

    override fun loadCurrencies(connected: Boolean) {

        if (!connected) {
            view.showApiCallError()
            return
        }

        disposables.add(currencyDao.checkIfCurrenciesExist()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    //First check if the database is populated with currency
                    // values
                    if (it.isEmpty()) {
                        view.showEmptyCurrenciesError()
                    } else {
                        view.currenciesExist()
                    }

                }
                .map {
                    //Each time the currency table is updated, this flowable gets notified.
                    getCurrenciesFlowable()
                            .subscribe()
                }
                .map {
                    loadData() //Then get the data from the API
                            .map { saveDataInDb(it) }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                view.onDatabaseUpdateSuccess()
                            }, { it.printStackTrace() })
                }
                .subscribe())

    }

    /**
     * Loads all the users cards and populates the recyclerview adapter
     */
    override fun loadCards() {
        disposables.add(cardDao.getAllCards()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cards ->
                    if (!cards.isEmpty()) {
                        view.cardsExist()
                        view.updateRecyclerView(cards)
                    } else {
                        view.showEmptyCardsError()
                    }

                })) }

    /**
     * Helper method that makes the API call and returns a list of all the exchange rates
     */
    private fun loadData(): Single<MutableList<Currency>> {
        val cryptoApi: CryptoCompareService = CryptoCompareService.create()

        return cryptoApi.getRates(Constants.currenciesString)
                .subscribeOn(scheduler)
                .doOnError { view.showApiCallError() }
                .flatMap { rates: ExchangeRate ->
                    val combinedList: MutableList<Currency> = ArrayList()

                    combinedList.addAll(createCurrencyObjects("BTC", rates.btcRates))
                    combinedList.addAll(createCurrencyObjects("ETH", rates.ethRates))

                    return@flatMap Single.just(combinedList)
                }
    }

    /**
     * Helper method. Each time the currency table is updated, this observable gets notified with
     * the new currencies and each card a user has saved gets updated with the new values.
     */
    private fun getCurrenciesFlowable(): Flowable<Unit> {
        return currencyDao.getAllCurrencies()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .map { currencies ->
                    if (!currencies.isEmpty()) {
                        view.currenciesExist()
                        updateCards()
                                .subscribe()
                    }
                }
    }

    /**
     * Gets all the cards and updates them with the new currencies
     */
    private fun updateCards(): Single<List<Card>> {
        return cardDao.getAllCards()
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { cards ->
                    run {
                        if (!cards.isEmpty()) {
                            for (card in cards) {
                                currencyDao.getConversionRate(card.from, card.to) //Get the new
                                        // values for currencies and update the cards
                                        .subscribeOn(scheduler)
                                        .subscribe({ amount ->
                                            cardDao.updateAmount(amount, card
                                                    .from, card.to)
                                        }, { e -> e.printStackTrace() })
                            }
                            view.updateRecyclerView(cards)
                        } else {
                            view.showEmptyCardsError()
                        }
                    }
                }
                .doOnError { e -> e.printStackTrace() }
    }

    override fun clearDisposables() {
        disposables.clear()
    }

    private fun createCurrencyObjects(from: String, map: HashMap<String, Double>): List<Currency> {
        val list = ArrayList<Currency>(0)

        for (key in map.keys) {
            val amount: Double? = map[key]
            list.add(Currency(0, from, key, amount!!))
        }

        return list
    }

    private fun saveDataInDb(list: List<Currency>) {
        currencyDao.insertAllCurrencies(list)
    }

}

