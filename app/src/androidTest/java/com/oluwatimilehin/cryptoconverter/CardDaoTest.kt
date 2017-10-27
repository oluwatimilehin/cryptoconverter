package com.oluwatimilehin.cryptoconverter

import android.database.sqlite.SQLiteConstraintException
import com.oluwatimilehin.cryptoconverter.data.Card
import com.oluwatimilehin.cryptoconverter.data.Currency
import junit.framework.Assert.fail
import org.junit.Before
import org.junit.Test

/**
 * Created by Oluwatimilehin on 26/10/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class CardDaoTest : BaseDaoTest() {
    lateinit var cardOne: Card
    lateinit var cardTwo: Card

    @Before
    override fun setUp() {
        super.setUp()

        cardOne = Card(0, "US Dollars", "BTC", "NGN", 200.0, 2333)
        cardTwo = Card(0, "Euros", "ETH", "NGN", 211.33, 1233)

        cardDao.insert(cardOne)
        cardDao.insert(cardTwo)
    }

    @Test
    fun insertCardsAndValidateSize() {

        try {
            val duplicateCard = Card(0, "Euros", "BTC", "BTN", 211.33, 1233)
            cardDao.insert(duplicateCard)

            fail()
        } catch (e: Exception) {
            (e as? SQLiteConstraintException)?.printStackTrace()
        }

        cardDao.getAllCards()
                .test()
                .assertValue { cards -> cards.size == 2 }
    }

    @Test
    fun getActualConversionRateAndTestUpdate() {
        currencyDao.insertAllCurrencies(listOfCurrencies)

        currencyDao.getAllCurrencies()
                .map { currencies ->
                    cardDao.getAllCards()
                            .doOnSuccess { cards ->
                                run {
                                    for (card in cards) {
                                        currencyDao.getConversionRate(card.from, card.to)
                                                .subscribe({ amount ->
                                                    cardDao.updateAmount(amount, card
                                                            .from, card.to)
                                                }, { e -> e.printStackTrace() })
                                    }
                                }
                            }
                            .subscribe()
                }
                .subscribe()

        val currencyFour = Currency(0, "ETH", "EUR", 212.12)
        val currencyThreeConflict = Currency(0, "ETH", "NGN", 222.23)
        val currencyFive = Currency(0, "ETH", "EUR", 211.23)
        val currencySix = Currency(0, "BTC", "NGN", 211.23)
        listOfCurrencies.addAll(listOf(currencyFour, currencyThreeConflict, currencyFive, currencySix))

        currencyDao.insertAllCurrencies(listOfCurrencies)

        cardDao.getCard("BTC", "NGN")
                .test()
                .assertValue { card -> card.amount == 211.23 }

        cardDao.getCard("ETH", "NGN")
                .test()
                .assertValue { card -> card.amount == 222.23 }

        cardDao.insert(Card(0, "Dollars", "BTH", "QWE", 38383.1, 11))

        cardDao.getAllCards()
                .test()
                .assertValue { cards -> cards.size == 3 }

    }
}