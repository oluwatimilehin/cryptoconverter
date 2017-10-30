package com.oluwatimilehin.cryptoconverter

import com.oluwatimilehin.cryptoconverter.addcard.AddCardContract
import com.oluwatimilehin.cryptoconverter.cards.CardsContract
import com.oluwatimilehin.cryptoconverter.data.CardDao
import com.oluwatimilehin.cryptoconverter.data.CurrencyDao
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oluwatimilehin on 30/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
abstract class BasePresenter{
    lateinit var cardsView: CardsContract.View;
    lateinit var addCardsView: AddCardContract.View
    val disposables: CompositeDisposable = CompositeDisposable();
    val scheduler: Scheduler = Schedulers.single()
    lateinit var cardDao: CardDao
    lateinit var currencyDao: CurrencyDao
}