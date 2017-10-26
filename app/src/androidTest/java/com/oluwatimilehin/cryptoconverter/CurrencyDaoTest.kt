package com.oluwatimilehin.cryptoconverter
import com.oluwatimilehin.cryptoconverter.data.Currency
import org.junit.After
import org.junit.Test

/**
 * Created by Oluwatimilehin on 25/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CurrencyDaoTest : BaseDaoTest(){

    @Test
    fun insertCurrenciesAndValidateSize(){
        currencyDao.insertAllCurrencies(listOfCurrencies)
        currencyDao.getAllCurrencies()
                .test()
                .assertValue { currencies -> currencies.size == 3 }

        val currencyFour = Currency(0, "ETH", "EUR", 212.12)
        val currencyThreeConflict = Currency(0, "ETH", "NGN", 222.23)
        listOfCurrencies.addAll(listOf(currencyFour, currencyThreeConflict))

        //Test that there is a replacement if a conflict happens
        currencyDao.insertAllCurrencies(listOfCurrencies)
        currencyDao.getAllCurrencies()
                .test()
                .assertValue { currencies -> currencies.size == 4 }
    }

    @Test
    fun getCorrectConversionRate(){
        currencyDao.insertAllCurrencies(listOfCurrencies)

        currencyDao.getConversionRate("ETH","NGN")
                .test()
                .assertValue { value -> value == 344.24 }
    }

    @After
    fun closeDb(){
        appDatabase.close()
    }
}