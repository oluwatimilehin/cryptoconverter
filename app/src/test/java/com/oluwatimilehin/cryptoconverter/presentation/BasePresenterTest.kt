package com.oluwatimilehin.cryptoconverter.presentation

import com.oluwatimilehin.cryptoconverter.data.di.RunOn
import com.oluwatimilehin.cryptoconverter.data.models.Card
import com.oluwatimilehin.cryptoconverter.data.models.Currency
import com.oluwatimilehin.cryptoconverter.data.repositories.CardsRepository
import com.oluwatimilehin.cryptoconverter.data.repositories.CurrencyRepository
import com.oluwatimilehin.cryptoconverter.utils.schedulers.SchedulerType
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by Oluwatimilehin on 04/12/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
abstract class BasePresenterTest{

    @Mock
    lateinit var cardsRepository: CardsRepository

    @Mock
    lateinit var currencyRepository: CurrencyRepository

    @RunOn(SchedulerType.MAIN)
    lateinit var mainThread: Scheduler

    @RunOn(SchedulerType.IO)
    lateinit var ioThread: Scheduler

    lateinit var scheduler: TestScheduler

    lateinit var cardOne: Card
    lateinit var cardTwo: Card

    lateinit var currencyOne: Currency
    lateinit var currencyTwo: Currency


    @Before
    open fun setUp(){
        MockitoAnnotations.initMocks(this)

        scheduler = TestScheduler()
        mainThread = scheduler
        ioThread = scheduler

        cardOne = Card(0, "Naira", "BTC", "ETH", 2000.3, 1)
        cardTwo = Card(0, "Naira", "BTC", "ETH", 2000.3, 1)

        currencyOne = Currency(0, "BTC", "ETH", 222.34)
        currencyTwo = Currency(0, "BTC", "ETH", 222.34)
    }
}