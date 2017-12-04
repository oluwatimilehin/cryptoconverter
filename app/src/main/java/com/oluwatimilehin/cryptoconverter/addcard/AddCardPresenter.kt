package com.oluwatimilehin.cryptoconverter.addcard

import com.mynameismidori.currencypicker.ExtendedCurrency
import com.oluwatimilehin.cryptoconverter.data.di.RunOn
import com.oluwatimilehin.cryptoconverter.data.models.Card
import com.oluwatimilehin.cryptoconverter.data.repositories.CardsRepository
import com.oluwatimilehin.cryptoconverter.data.repositories.CurrencyRepository
import com.oluwatimilehin.cryptoconverter.utils.ExtendedCurrencyWrapper
import com.oluwatimilehin.cryptoconverter.utils.schedulers.SchedulerType
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 30/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class AddCardPresenter @Inject constructor(private val currencyRepository: CurrencyRepository, private val
cardsRepository: CardsRepository, val view: AddCardContract.View, @RunOn(SchedulerType.IO) private val
                                           ioScheduler:
                                           Scheduler, @RunOn(SchedulerType.MAIN) private val
mainThread: Scheduler, private val extendedCurrencyWrapper: ExtendedCurrencyWrapper) :
        AddCardContract.Presenter {

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun clearDisposables() {
        disposables.clear()
    }


    override fun saveCard(from: String, to: String) {
        val currency: ExtendedCurrency = extendedCurrencyWrapper.getCurrencyByName(to)

        disposables.add(currencyRepository.getAmountValue(from, currency.code)
                .subscribeOn(ioScheduler)
                .doOnError { it.printStackTrace() }
                .flatMapCompletable {
                    val flag = currency.flag
                    val name = currency.name
                    val card = Card(0, name, from, currency.code, it, flag)

                    return@flatMapCompletable Completable.fromAction {
                        cardsRepository.saveCard(card)
                    }
                }
                .observeOn(mainThread)
                .subscribe({view.saveCardSuccess()}, { view.cardExistsError()})

        )


    }

}