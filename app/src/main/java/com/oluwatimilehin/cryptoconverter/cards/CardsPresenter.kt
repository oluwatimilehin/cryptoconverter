package com.oluwatimilehin.cryptoconverter.cards

import com.oluwatimilehin.cryptoconverter.data.di.RunOn
import com.oluwatimilehin.cryptoconverter.data.models.Card
import com.oluwatimilehin.cryptoconverter.data.repositories.CardsRepository
import com.oluwatimilehin.cryptoconverter.data.repositories.CurrencyRepository
import com.oluwatimilehin.cryptoconverter.utils.schedulers.SchedulerType
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by Oluwatimilehin on 13/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardsPresenter @Inject constructor(val cardsRepository: CardsRepository, val currencyRepository:
CurrencyRepository, val view: CardsContract.View, @RunOn(SchedulerType.IO) val ioScheduler:
                                         Scheduler, @RunOn(SchedulerType.MAIN) val mainThread: Scheduler) :
        CardsContract.Presenter {

    var disposables: CompositeDisposable = CompositeDisposable()

    override fun checkIfCurrenciesExist() {
        val disposable = currencyRepository.checkIfCurrenciesExist()
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .subscribe({ view.currenciesExist() }, { view.showEmptyCurrenciesError() })

        registerForUpdates()

        disposables.add(disposable)
    }

    override fun deleteAllCards() {
        disposables.add(Completable.fromAction { cardsRepository.deleteAll() }
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
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
        disposables.add(Completable.fromAction { cardsRepository.deleteCard(card) }
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showCardDeleted()
                    loadCards()
                }))
    }

    override fun addNewCard() {
        view.showAddCard()
    }

    private fun registerForUpdates() {
        disposables.add(currencyRepository.getObservableCurrencies()
                .subscribeOn(ioScheduler)
                .flatMapSingle { cardsRepository.getAllCards() }
                .observeOn(mainThread)
                .doOnError { view.showEmptyCardsError() }
                .observeOn(ioScheduler)
                .flatMapIterable { it }
                .flatMap({ it: Card ->
                    currencyRepository.getAmountValue(it.from, it.to)
                            .toFlowable()
                }, { card: Card, value: Double ->
                    Pair(card, value)
                })
                .map { cardsRepository.updateCard(it.first, it.second) }
                .toList()
                .flatMap { cardsRepository.getAllCards() }
                .observeOn(mainThread)
                .subscribe({ cards -> view.updateRecyclerView(cards) }, { it.printStackTrace() }))
    }

    override fun loadCurrencies(connected: Boolean) {
        if (!connected) {
            view.showApiCallError()
            return
        }

        disposables.add(currencyRepository.loadCurrencies()
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .subscribe({ view.onDatabaseUpdateSuccess() }, { view.showApiCallError() })
        )
    }

    override fun clearDisposables() {
        disposables.clear()
    }

    /**
     * Loads all the users cards and populates the recyclerview adapter
     */
    override fun loadCards() {
        disposables.add(cardsRepository.getAllCards()
                .subscribeOn(ioScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ cards ->
                    kotlin.run { view.cardsExist()
                                 view.updateRecyclerView(cards)
                    }
                }, {view.showEmptyCardsError()}))
    }

//    /**
//     * Gets all the cards and updates them with the new currencies
//     */
//    private fun updateCards(): Single<List<Card>> {
//        return cardsRepository.getAllCards()
//                .subscribeOn(ioScheduler)
//                .observeOn(mainThread)
//                .doOnSuccess { cards ->
//                    run {
//                        if (!cards.isEmpty()) {
//                            for (card in cards) {
//                                currencyDao.getConversionRate(card.from, card.to) //Get the new
//                                        // values for currencies and update the cards
//                                        .subscribeOn(ioScheduler)
//                                        .subscribe({ amount ->
//                                            cardDao.updateAmount(amount, card
//                                                    .from, card.to)
//                                        }, { e -> e.printStackTrace() })
//                            }
//                            view.updateRecyclerView(cards)
//                        } else {
//                            view.showEmptyCardsError()
//                        }
//                    }
//                }
//                .doOnError { e -> e.printStackTrace() }
//    }


//        disposables.add(currencyRepository.doCurrenciesExist()
//                .subscribeOn(ioScheduler)
//                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorResumeNext { Single.error<List<Currency>>(it) }
//                .doOnSuccess { view.currenciesExist() }
//                .doOnError { view.showEmptyCurrenciesError() }
//
//                .doAfterTerminate { currencyRepository.getObservableCurrencies() }
//                .observeOn(ioScheduler)
//                .filter({it.isNotEmpty()})
//
//                .observeOn(mainThread)
//                .doAfterSuccess{view.currenciesExist()}
//
//                .observeOn(ioScheduler)
//                .map{ cardsRepository.getAllCards()}
//
//
//
//
//
////                .map {
////                    //Each time the currency table is updated, this flowable gets notified.
////                    getCurrenciesFlowable()
////                            .subscribe()
////                }
//                .map {
//                    loadData() //Then get the data from the API
//                            .map { saveDataInDb(it) }
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe({
//                                view.onDatabaseUpdateSuccess()
//                            }, { it.printStackTrace() })
//                }
//                .subscribe())


//                    /**
//                     * Helper method. Each time the currency table is updated, this observable gets notified with
//                     * the new currencies and each card a user has saved gets updated with the new values.
//                     */
//                    private fun getCurrenciesFlowable(): Flowable<Unit> {
//                        return currencyDao.getAllCurrencies()
//                                .subscribeOn(ioScheduler)
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .map { currencies ->
//                                    if (!currencies.isEmpty()) {
//                                        view.currenciesExist()
//                                        updateCards()
//                                                .subscribe()
//                                    }
//                                }
//                    }
//

//    /**
//     * Helper method that makes the API call and returns a list of all the exchange rates
//     */
//    private fun loadData(): Single<MutableList<Currency>> {
//        val cryptoApi: CryptoCompareService = CryptoCompareService.create()
//
//        return cryptoApi.getRates(Constants.currenciesString)
//                .subscribeOn(ioScheduler)
//                .doOnError { view.showApiCallError() }
//                .flatMap { rates: ExchangeRate ->
//                    val combinedList: MutableList<Currency> = ArrayList()
//
//                    combinedList.addAll(createCurrencyObjects("BTC", rates.btcRates))
//                    combinedList.addAll(createCurrencyObjects("ETH", rates.ethRates))
//
//                    return@flatMap Single.just(combinedList)
//                }



                }


