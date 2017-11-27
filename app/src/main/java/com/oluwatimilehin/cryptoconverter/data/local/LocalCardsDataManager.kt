package com.oluwatimilehin.cryptoconverter.data.local

import com.oluwatimilehin.cryptoconverter.data.daos.CardDao
import com.oluwatimilehin.cryptoconverter.data.models.Card
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Oluwatimilehin on 23/11/2017.
 * oluwatimilehinadeniran@gmail.com.
 */
class LocalCardsDataManager public @Inject constructor(private val cardDao: CardDao) : CardsDataManager {
    override fun updateAmount(card: Card, value: Double) {
        cardDao.updateAmount(value, card.from, card.to)
    }

    override fun saveCard(card: Card) {
        cardDao.insert(card)
    }

    override fun deleteCard(card: Card) {
        cardDao.deleteCard(card.from, card.to)
    }

    override fun deleteCards() {
        cardDao.deleteAllCards()
    }

    override fun getCards(): Single<List<Card>> = cardDao.getAllCards()

}