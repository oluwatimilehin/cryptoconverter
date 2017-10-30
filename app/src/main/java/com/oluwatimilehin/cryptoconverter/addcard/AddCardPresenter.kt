package com.oluwatimilehin.cryptoconverter.addcard

import android.database.sqlite.SQLiteConstraintException
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.oluwatimilehin.cryptoconverter.App
import com.oluwatimilehin.cryptoconverter.BasePresenter
import com.oluwatimilehin.cryptoconverter.data.Card

/**
 * Created by Oluwatimilehin on 30/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class AddCardPresenter : AddCardContract.Presenter, BasePresenter(){
    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attachView(view: AddCardContract.View) {
        this.addCardsView = view
        currencyDao = App.database.currencyDao()
        cardDao = App.database.cardDao()
    }

    override fun saveCard(from: String, to: String) {
        disposables.add(currencyDao.getConversionRate(from, to)
                .subscribeOn(scheduler)
                .subscribe({ amount ->
                    val currency: ExtendedCurrency = ExtendedCurrency.getCurrencyByISO(to)
                    val flag = currency.flag
                    val name = currency.name

                    val card = Card(0, name, from, to, amount, flag)

                    try {
                        cardDao.insert(card)
                        addCardsView.saveCardSuccess()

                    }catch (e: SQLiteConstraintException){
                        addCardsView.cardExistsError()
                    }
                })
        )


    }

}