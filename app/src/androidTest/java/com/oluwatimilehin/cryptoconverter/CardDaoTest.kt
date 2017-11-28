package com.oluwatimilehin.cryptoconverter

import android.database.sqlite.SQLiteConstraintException
import com.oluwatimilehin.cryptoconverter.data.models.Card
import com.oluwatimilehin.cryptoconverter.data.models.Currency
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
            val duplicateCard = Card(0, "Euros", "BTC", "NGN", 211.33, 1233)
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
                .flatMapSingle { cardDao.getAllCards() }
                .flatMapIterable { it }
                .flatMap({it: Card ->
                    currencyDao.getConversionRate(it.from, it.to).toFlowable()
                }, {card: Card, value: Double ->
                    Pair(card, value) })
                .map{cardDao.updateAmount(it.second, it.first.from, it.first.to)}
                .toList()
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