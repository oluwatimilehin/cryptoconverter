package com.oluwatimilehin.cryptoconverter.addcard

import com.mynameismidori.currencypicker.ExtendedCurrency
import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.BasePresenter
import com.oluwatimilehin.cryptoconverter.data.models.Card
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Oluwatimilehin on 30/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class AddCardPresenter : AddCardContract.Presenter, BasePresenter() {
    override fun clearDisposables() {
        disposables.clear()
    }

    override fun attachView(view: AddCardContract.View) {
        this.addCardsView = view
        currencyDao = App.database.currencyDao()
        cardDao = App.database.cardDao()
    }

    override fun saveCard(from: String, to: String) {
        val currency: ExtendedCurrency = ExtendedCurrency.getCurrencyByName(to)

        disposables.add(currencyDao.getConversionRate(from, currency.code)
                .subscribeOn(scheduler)
                .doOnError { it.printStackTrace() }
                .flatMapCompletable {
                    val flag = currency.flag
                    val name = currency.name
                    val card = Card(0, name, from, currency.code, it, flag)

                    return@flatMapCompletable Completable.fromAction {
                        cardDao.insert(card)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete({ addCardsView.saveCardSuccess() })
                .doOnError { addCardsView.cardExistsError() }
                .subscribe({}, {it.printStackTrace()})

        )


    }

}