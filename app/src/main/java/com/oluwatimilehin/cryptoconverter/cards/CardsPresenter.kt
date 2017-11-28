package com.oluwatimilehin.cryptoconverter.cards

import com.oluwatimilehin.cryptoconverter.data.di.RunOn
import com.oluwatimilehin.cryptoconverter.data.models.Card
import com.oluwatimilehin.cryptoconverter.data.repositories.CardsRepository
import com.oluwatimilehin.cryptoconverter.data.repositories.CurrencyRepository
import com.oluwatimilehin.cryptoconverter.utils.schedulers.SchedulerType
import io.reactivex.Completable
import io.reactivex.Scheduler
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

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun checkIfCurrenciesExist() {
        val disposable = currencyRepository.checkIfCurrenciesExist()
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .subscribe({ view.currenciesExist() }, { view.showEmptyCurrenciesError() })

        disposables.add(disposable)
    }

    override fun deleteAllCards() {
        disposables.add(Completable.fromAction { cardsRepository.deleteAll() }
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .doOnComplete({loadCards()})
                .doOnError({it.printStackTrace()})
                .subscribe()
        )
    }

    override fun navigateToConversionScreen(card: Card) {
        val from = card.from
        val to = card.to
        val amount = card.amount

        view.showConversionScreen(from, to, amount)
    }

    override fun deleteCard(card: Card) {
        disposables.add(Completable.fromAction { cardsRepository.deleteCard(card) }
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .doOnComplete({loadCards()})
                .subscribe({
                    view.showCardDeleted()
                }))
    }

    override fun navigateToAddCardScreen() {
        view.showAddCard()
    }

    fun registerForUpdates() {
        disposables.add(currencyRepository.getObservableCurrencies()
                .subscribeOn(ioScheduler)
                .flatMapSingle { cardsRepository.getAllCards() }
                .observeOn(mainThread)
                .doOnError({it.printStackTrace()})
                .observeOn(ioScheduler)
                .flatMapIterable { it }
                .flatMap({ it: Card ->
                    currencyRepository.getAmountValue(it.from, it.to)
                            .toFlowable()
                }, { card: Card, value: Double ->
                    Pair(card, value)
                })
                .map{ cardsRepository.updateCard(it.first, it.second) }
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
                .map { currencyRepository.saveCurrencies(it) }
                .observeOn(mainThread)
                .subscribe(
                        {
                            view.onDatabaseUpdateSuccess()
                            view.currenciesExist()
                        },
                        { view.showApiCallError() })
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
                .observeOn(mainThread)
                .subscribe({ cards ->
                    kotlin.run {
                        view.cardsExist()
                        view.updateRecyclerView(cards)
                    }
                }, { view.showEmptyCardsError() }))
    }

}


