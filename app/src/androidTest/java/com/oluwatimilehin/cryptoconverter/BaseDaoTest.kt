package com.oluwatimilehin.cryptoconverter

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.oluwatimilehin.cryptoconverter.data.AppDatabase
import com.oluwatimilehin.cryptoconverter.data.Currency
import com.oluwatimilehin.cryptoconverter.data.CurrencyDao
import org.junit.Before
import org.junit.Rule

/**
 * Created by Oluwatimilehin on 26/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
abstract class BaseDaoTest{
    lateinit var appDatabase: AppDatabase
    lateinit var currencyOne: Currency
    lateinit var currencyTwo: Currency
    lateinit var currencyThree: Currency
    lateinit var listOfCurrencies: MutableList<Currency>
    lateinit var currencyDao: CurrencyDao

    @JvmField @Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        appDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        currencyDao = appDatabase.currencyDao()

        currencyOne = Currency(0, "BTC", "NGN", 4000.0)
        currencyTwo = Currency(0, "ETH", "NGN", 344.24)
        currencyThree = Currency(0, "BTC", "USD", 400.12)

        listOfCurrencies = ArrayList()
        listOfCurrencies.addAll(listOf(currencyOne, currencyTwo, currencyThree))
    }
}